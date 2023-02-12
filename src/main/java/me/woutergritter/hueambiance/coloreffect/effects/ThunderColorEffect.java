package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.LightningStrikeEvent;

import java.awt.*;

@RegisteredColorEffect
public class ThunderColorEffect extends ListenerColorEffect {

    private final Player player;

    private long lastLightningStrike;

    public ThunderColorEffect(Player player) {
        this.player = player;
    }

    @EventHandler
    public void onLightningStrikeEvent(LightningStrikeEvent e) {
        if (e.getWorld() == player.getWorld()) {
            lastLightningStrike = System.currentTimeMillis();
        }
    }

    @Override
    public boolean isActive() {
        long elapsed = System.currentTimeMillis() - lastLightningStrike;
        if (elapsed < 500) {
            return true;
        } else if (elapsed < 1000) {
            return false;
        } else if (elapsed < 1500) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public int getPriority() {
        return 100;
    }
}
