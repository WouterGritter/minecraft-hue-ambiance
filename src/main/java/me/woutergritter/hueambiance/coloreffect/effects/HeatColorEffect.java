package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.ColorEffectHelpers;
import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import me.woutergritter.hueambiance.util.CachedMethod;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Set;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static me.woutergritter.hueambiance.util.ColorUtils.randomColor;
import static me.woutergritter.hueambiance.util.NearbyBlocks.getNearbyBlockOfTypes;
import static org.bukkit.Material.FIRE;
import static org.bukkit.Material.LAVA;

@RegisteredColorEffect
public class HeatColorEffect implements ColorEffect {

    private static final Set<Material> HEAT_MATERIALS = Set.of(
            FIRE, LAVA
    );

    private final Player player;
    private final CachedMethod<Boolean> isActive = new CachedMethod<>(500);
    private final CachedMethod<Color> getColor = new CachedMethod<>(2000);

    public HeatColorEffect(Player player) {
        ColorEffectHelpers.findRegisteredColorEffectClasses()
                .forEach(clazz -> getPlugin().getLogger().info("Found registered color effect: " + clazz.getName()));
        this.player = player;
    }

    @Override
    public boolean isActive() {
        return isActive.getValue(() -> getNearbyBlockOfTypes(player.getLocation(), 10, HEAT_MATERIALS).isPresent());
    }

    @Override
    public Color getColor() {
        return getColor.getValue(() -> randomColor(new Color(252, 53, 3), new Color(252, 181, 3)));
    }

    @Override
    public int getPriority() {
        return 12;
    }
}
