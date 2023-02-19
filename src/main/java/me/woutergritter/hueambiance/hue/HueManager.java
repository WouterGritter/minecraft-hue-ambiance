package me.woutergritter.hueambiance.hue;

import io.github.zeroone3010.yahueapi.Hue;
import io.github.zeroone3010.yahueapi.HueBridge;
import io.github.zeroone3010.yahueapi.Room;
import io.github.zeroone3010.yahueapi.State;
import io.github.zeroone3010.yahueapi.discovery.HueBridgeDiscoveryService;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static org.bukkit.persistence.PersistentDataType.INTEGER;
import static org.bukkit.persistence.PersistentDataType.STRING;

public class HueManager {

    private static final String APP_NAME = "wgplugin";

    private final NamespacedKey hueAmbianceEnabledKey;
    private final NamespacedKey hueAmbianceRoomNameKey;

    private Hue hue;

    public HueManager() {
        hueAmbianceEnabledKey = new NamespacedKey(getPlugin(), "hueAmbianceEnabled");
        hueAmbianceRoomNameKey = new NamespacedKey(getPlugin(), "hueAmbianceRoomName");
    }

    public boolean canSetStateFor(Player player) {
        return hue != null &&
                isEnabledFor(player) &&
                getRoomFor(player).isPresent();
    }

    public void updateColor(Player player, java.awt.Color color) {
        if (hue == null) {
            throw new IllegalStateException("Connection to the Hue bridge is not set up yet.");
        }

        if (!canSetStateFor(player)) {
            throw new IllegalStateException("Can't update state for this player. Please call canSetStateFor first.");
        }

        Room room = getRoomFor(player).orElseThrow();

        if (color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) {
            room.turnOff();
        } else {
            float[] hsb = java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
            room.setState(
                    State.builder()
                            .hue((int) (hsb[0] * 65280))
                            .saturation((int) (hsb[1] * 254))
                            .brightness(254)
                            .transitionTime(1)
                            .on()
            );
        }
    }

    public boolean connect() {
        HueCredentials credentials = loadCredentials().orElse(null);
        if (credentials == null) {
            return false;
        }

        try {
            hue = new Hue(credentials.address(), credentials.apiKey());
            hue.refresh();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            hue = null;
        }

        return false;
    }

    public CompletableFuture<HueBridge> discoverBridge() {
        var future = new CompletableFuture<HueBridge>();
        new HueBridgeDiscoveryService()
                .discoverBridges(future::complete);

        return future;
    }

    public Optional<String> obtainApiKey(String bridgeIp) {
        var apiKeyFuture = Hue.hueBridgeConnectionBuilder(bridgeIp)
                .initializeApiConnection(APP_NAME);

        try {
            String apiKey = apiKeyFuture.get();
            getPlugin().getLogger().info("Obtained Hue API key " + apiKey);
            return Optional.of(apiKey);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Stream<String> getAvailableRoomNames() {
        if (hue == null) {
            return Stream.empty();
        }

        return hue.getRooms().stream()
                .map(Room::getName);
    }

    public void storeCredentials(HueCredentials credentials) {
        getPlugin().getConfig().set("hue.address", credentials.address());
        getPlugin().getConfig().set("hue.api-key", credentials.apiKey());
        getPlugin().saveConfig();
    }

    public Optional<HueCredentials> loadCredentials() {
        String address = getPlugin().getConfig().getString("hue.address");
        String apiKey = getPlugin().getConfig().getString("hue.api-key");

        if (address == null || apiKey == null) {
            return Optional.empty();
        }

        return Optional.of(new HueCredentials(address, apiKey));
    }

    public void setEnabledFor(Player player, boolean enabled) {
        player.getPersistentDataContainer().set(hueAmbianceEnabledKey, INTEGER, enabled ? 1 : 0);
    }

    public boolean isEnabledFor(Player player) {
        return player.getPersistentDataContainer().getOrDefault(hueAmbianceEnabledKey, INTEGER, 0) != 0;
    }

    public void setRoomNameFor(Player player, String roomName) {
        player.getPersistentDataContainer().set(hueAmbianceRoomNameKey, STRING, roomName);
    }

    public Optional<String> getRoomNameFor(Player player) {
        return Optional.ofNullable(
                player.getPersistentDataContainer().get(hueAmbianceRoomNameKey, STRING)
        );
    }

    public Optional<Room> getRoomFor(Player player) {
        if (hue == null) {
            return Optional.empty();
        }

        return getRoomNameFor(player)
                .flatMap(hue::getRoomByName);
    }
}
