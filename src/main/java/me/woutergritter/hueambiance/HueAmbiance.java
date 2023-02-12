package me.woutergritter.hueambiance;

import me.woutergritter.hueambiance.coloreffect.ColorManager;
import me.woutergritter.hueambiance.commands.ConnecthueCommand;
import me.woutergritter.hueambiance.hue.HueManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class HueAmbiance extends JavaPlugin {

    private static HueAmbiance instance;

    private HueManager hueManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        hueManager = new HueManager();
        boolean connectedToBridge = hueManager.connect();
        if (connectedToBridge) {
            Bukkit.getLogger().info("Connected to Hue bridge.");
        } else {
            Bukkit.getLogger().info("Could not connect to Hue bridge.");
        }

        new ColorManager();

        // Register commands
        Set.of(
                new ConnecthueCommand()
        ).forEach(c -> c.register(this));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public HueManager getHueManager() {
        return hueManager;
    }

    public static HueAmbiance getPlugin() {
        return instance;
    }
}
