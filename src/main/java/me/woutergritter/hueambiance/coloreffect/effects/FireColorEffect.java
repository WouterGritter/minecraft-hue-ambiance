package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;

import static me.woutergritter.hueambiance.util.NearbyBlocks.getNearbyBlockOfType;

@RegisteredColorEffect
public class FireColorEffect implements ColorEffect {

    private final Player player;
    private final ColorRandomizer randomizer;

    public FireColorEffect(Player player) {
        this.player = player;
        this.randomizer = new ColorRandomizer(new Color(252, 53, 3), new Color(252, 181, 3), 2000);
    }

    @Override
    public boolean isActive() {
        return getNearbyBlockOfType(player.getLocation(), 10, Material.FIRE).isPresent();
    }

    @Override
    public Color getColor() {
        return randomizer.getColor();
    }

    @Override
    public int getPriority() {
        return 12;
    }
}
