package me.woutergritter.hueambiance.coloreffect;

import me.woutergritter.hueambiance.coloreffect.effects.ColorEffect;
import me.woutergritter.hueambiance.coloreffect.effects.CompositeColorEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;

public class PlayerColorEffectBridge {

    private final ColorEffect colorEffect;
    private final Player player;

    private Color previousColor = null;

    public PlayerColorEffectBridge(Class<? extends ColorEffect> effectClass, Player player) {
        this.colorEffect = instantiateColorEffect(effectClass, player)
                .orElseThrow(IllegalArgumentException::new);

        this.player = player;
    }

    public PlayerColorEffectBridge(Collection<Class<? extends ColorEffect>> effectClasses, Player player) {
        this.colorEffect = new CompositeColorEffect(Color.BLACK, instantiateColorEffects(effectClasses, player));
        this.player = player;
    }

    public void update() {
        // TODO: Add support for multiple players.
        Color color = colorEffect.getColor();
        if (!color.equals(previousColor)) {
            try {
                getPlugin().getHueManager().updateColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
                this.previousColor = color;
            } catch (Exception e) {
                Bukkit.getLogger().warning("Could not update color. See stack-trace.");
                e.printStackTrace();
            }
        }
    }

    public void setup() {
        colorEffect.setup();
    }

    public void dispose() {
        colorEffect.dispose();
    }

    public ColorEffect getColorEffect() {
        return colorEffect;
    }

    public Player getPlayer() {
        return player;
    }

    private static Optional<ColorEffect> instantiateColorEffect(Class<? extends ColorEffect> clazz, Player player) {
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

        Bukkit.getLogger().warning("Could not instantiate ColorEffect " + clazz.getName() + ". Does it have the right constructor?");
        return Optional.empty();
    }

    private static Collection<ColorEffect> instantiateColorEffects(Collection<Class<? extends ColorEffect>> classes, Player player) {
        return classes.stream()
                .map(clazz -> instantiateColorEffect(clazz, player))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}
