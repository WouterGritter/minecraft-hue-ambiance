package me.woutergritter.hueambiance.coloreffect;

import me.woutergritter.hueambiance.coloreffect.effects.ColorEffect;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;

public class ColorEffectHelpers {

    private ColorEffectHelpers() {
    }

    public static Collection<ColorEffect> instantiateRegisteredColorEffects(Player player) {
        return findRegisteredColorEffectClasses()
                .stream()
                .map(clazz -> instantiateColorEffect(clazz, player))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    public static Collection<Class<? extends ColorEffect>> findRegisteredColorEffectClasses() {
        return new Reflections("me.woutergritter.hueambiance.coloreffect.effects")
                .getTypesAnnotatedWith(RegisteredColorEffect.class)
                .stream()
                .filter(ColorEffect.class::isAssignableFrom)
                .map(clazz -> (Class<? extends ColorEffect>) clazz)
                .collect(Collectors.toSet());
    }

    public static Optional<ColorEffect> instantiateColorEffect(Class<? extends ColorEffect> clazz, Player player) {
        try {
            var colorEffect = clazz.getConstructor().newInstance();
            return Optional.of(colorEffect);
        } catch (Exception ignore) {
        }

        try {
            var colorEffect = clazz.getConstructor(Player.class).newInstance(player);
            return Optional.of(colorEffect);
        } catch (Exception ignore) {
        }

        getPlugin().getLogger().warning("Could not instantiate ColorEffect " + clazz.getName() + ". Does it have the right constructor?");
        return Optional.empty();
    }
}
