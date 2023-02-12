package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.awt.*;

@RegisteredColorEffect
public class DamageColorEffect extends ListenerColorEffect {

    private final Player player;
    private long lastDamage;

    public DamageColorEffect(Player player) {
        this.player = player;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() == player) {
            lastDamage = System.currentTimeMillis();
        }
    }

    @Override
    public boolean isActive() {
        long elapsed = System.currentTimeMillis() - lastDamage;
        return elapsed < 750;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public int getPriority() {
        return 20;
    }
}
