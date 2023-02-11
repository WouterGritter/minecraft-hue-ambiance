package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;

import static me.woutergritter.hueambiance.util.NearbyBlocks.getNearbyBlockOfType;

@RegisteredColorEffect
public class FireColorEffect implements ColorEffect {

    private final Player player;

    private long lastColorChange;
    private Color color;

    public FireColorEffect(Player player) {
        this.player = player;
    }

    @Override
    public boolean isActive() {
        return getNearbyBlockOfType(player.getLocation(), 10, Material.FIRE).isPresent();
    }

    @Override
    public Color getColor() {
        long elapsed = System.currentTimeMillis() - lastColorChange;
        if (color == null || elapsed > 1000) {
            color = randomFireColor();
            lastColorChange = System.currentTimeMillis();
        }

        return color;
    }

    @Override
    public int getPriority() {
        return 12;
    }

    private Color randomFireColor() {
        return new Color(
                252,
                (int) (53 + Math.random() * 128),
                3
        );
    }
}
