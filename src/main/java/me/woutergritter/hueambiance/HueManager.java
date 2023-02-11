package me.woutergritter.hueambiance;

import io.github.zeroone3010.yahueapi.*;
import io.github.zeroone3010.yahueapi.discovery.HueBridgeDiscoveryService;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;

public class HueManager {

    private static final String APP_NAME = "wgplugin";

    private Hue hue;

    public void updateColor(float red, float green, float blue) {
        Room room = hue.getRoomByName("Woonkamer")
                .orElseThrow();

        room.getLights().forEach(light -> updateColor(light, red, green, blue));
    }

    private void updateColor(Light light, float red, float green, float blue) {
        light.setBrightness(100);
        light.setState(
                State.builder()
                        .color(Color.of(red, green, blue))
                        .on()
        );
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
            Bukkit.getLogger().info("Obtained Hue API key " + apiKey);
            return Optional.of(apiKey);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
