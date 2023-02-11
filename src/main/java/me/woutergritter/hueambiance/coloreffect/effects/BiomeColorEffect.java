package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.awt.*;

import static me.woutergritter.hueambiance.util.BiomeColors.getBiomeColor;

@RegisteredColorEffect
public class BiomeColorEffect implements ColorEffect {

    private final Player player;

    public BiomeColorEffect(Player player) {
        this.player = player;
    }

    @Override
    public boolean isActive() {
        return true; // Biome color effect is always active
    }

    @Override
    public Color getColor() {
        Biome biome = player.getLocation().getBlock().getBiome();
        return getBiomeColor(biome);
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
