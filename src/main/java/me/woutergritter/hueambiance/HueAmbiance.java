package me.woutergritter.hueambiance;

import me.woutergritter.hueambiance.coloreffect.ColorManager;
import me.woutergritter.hueambiance.commands.HueconnectCommand;
import me.woutergritter.hueambiance.commands.HueenableCommand;
import me.woutergritter.hueambiance.commands.HueroomCommand;
import me.woutergritter.hueambiance.hue.HueManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HueAmbiance extends JavaPlugin {

    private static HueAmbiance instance;

    private ExecutorService threadPool;
    private HueManager hueManager;
    private ColorManager colorManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        threadPool = Executors.newCachedThreadPool();

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
        threadPool.shutdown();
        instance = null;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
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
