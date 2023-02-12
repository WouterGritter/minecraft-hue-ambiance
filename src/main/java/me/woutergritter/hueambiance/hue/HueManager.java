package me.woutergritter.hueambiance.hue;

import io.github.zeroone3010.yahueapi.Hue;
import io.github.zeroone3010.yahueapi.HueBridge;
import io.github.zeroone3010.yahueapi.Room;
import io.github.zeroone3010.yahueapi.State;
import io.github.zeroone3010.yahueapi.discovery.HueBridgeDiscoveryService;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;

public class HueManager {

    private static final String APP_NAME = "wgplugin";

    private Hue hue;

    public boolean canSetStateFor(OfflinePlayer player) {
        if (hue == null) {
            return false;
        }

        // TODO: Add support for multiple players.
        return "TheWGBbroz".equals(player.getName());
    }

    public void updateColor(OfflinePlayer player, java.awt.Color color) {
        if (hue == null) {
            throw new IllegalStateException("Connection to the Hue bridge is not set up yet.");
        }

        // TODO: Add support for multiple players.
        Room room = hue.getRoomByName("Woonkamer")
                .orElseThrow();

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

        System.out.println("UPDATING STATE");
    }

    public void storeCredentials(String hueAddress, String apiKey) {
        getPlugin().getConfig().set("hue.address", hueAddress);
        getPlugin().getConfig().set("hue.api-key", apiKey);
        getPlugin().saveConfig();
    }

    public boolean connect() {
        String address = getPlugin().getConfig().getString("hue.address");
        String apiKey = getPlugin().getConfig().getString("hue.api-key");

        if (address == null || apiKey == null) {
            return false;
        }

        try {
            hue = new Hue(address, apiKey);
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
}
