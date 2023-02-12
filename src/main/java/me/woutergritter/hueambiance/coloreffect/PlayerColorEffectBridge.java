package me.woutergritter.hueambiance.coloreffect;

import me.woutergritter.hueambiance.coloreffect.effects.ColorEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.awt.*;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerColorEffectBridge {

    private final ColorEffect colorEffect;
    private final Player player;

    private Color previousColor = null;
    private BukkitTask updateTask = null;

    public PlayerColorEffectBridge(ColorEffect colorEffect, Player player) {
        this.colorEffect = colorEffect;
        this.player = player;
    }

    public void setup() {
        if (updateTask != null) {
            throw new IllegalStateException();
        }

        colorEffect.setup();

        updateTask = getScheduler().runTaskTimer(getPlugin(), this::update, 1, 1);
    }

    public void dispose() {
        if (updateTask == null) {
            throw new IllegalStateException();
        }

        updateTask.cancel();
        updateTask = null;

        colorEffect.dispose();
    }

    private void update() {
        Color color = colorEffect.getColor();
        if (!color.equals(previousColor)) {
            try {
                getPlugin().getHueManager().updateColor(player, color);
                this.previousColor = color;
            } catch (Exception e) {
                Bukkit.getLogger().warning("Could not update color. See stack-trace.");
                e.printStackTrace();
            }
        }
    }

    public ColorEffect getColorEffect() {
        return colorEffect;
    }

    public Player getPlayer() {
        return player;
    }
}
