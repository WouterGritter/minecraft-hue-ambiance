package me.woutergritter.hueambiance;

import me.woutergritter.hueambiance.coloreffect.ColorManager;
import me.woutergritter.hueambiance.commands.HueconnectCommand;
import me.woutergritter.hueambiance.commands.HueenableCommand;
import me.woutergritter.hueambiance.commands.HueroomCommand;
import me.woutergritter.hueambiance.hue.HueManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class HueAmbiance extends JavaPlugin {

    private static HueAmbiance instance;

    private HueManager hueManager;
    private ColorManager colorManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        hueManager = new HueManager();
        boolean connectedToBridge = hueManager.connect();
        if (connectedToBridge) {
            getLogger().info("Connected to Hue bridge.");
        } else {
            getLogger().info("Could not connect to Hue bridge.");
        }

        colorManager = new ColorManager();

        // Register commands
        Set.of(
                new HueconnectCommand(),
                new HueenableCommand(),
                new HueroomCommand()
        ).forEach(c -> c.register(this));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public HueManager getHueManager() {
        return hueManager;
    }

    public ColorManager getColorManager() {
        return colorManager;
    }

    public static HueAmbiance getPlugin() {
        return instance;
    }
}
