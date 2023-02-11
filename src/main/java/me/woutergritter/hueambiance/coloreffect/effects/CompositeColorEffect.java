package me.woutergritter.hueambiance.coloreffect.effects;

import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class CompositeColorEffect implements ColorEffect {

    private final Color defaultColor;
    private final Collection<ColorEffect> colorEffects;

    public CompositeColorEffect(Color defaultColor, Collection<ColorEffect> colorEffects) {
        this.defaultColor = defaultColor;
        this.colorEffects = colorEffects;
    }

    @Override
    public void setup() {
        colorEffects.forEach(ColorEffect::setup);
    }

    @Override
    public void dispose() {
        colorEffects.forEach(ColorEffect::dispose);
    }

    @Override
    public boolean isActive() {
        if (defaultColor != null) {
            return true;
        } else {
            return colorEffects.stream().anyMatch(ColorEffect::isActive);
        }
    }

    @Override
    public Color getColor() {
        return colorEffects.stream()
                .filter(ColorEffect::isActive)
                .max(Comparator.comparingInt(ColorEffect::getPriority))
                .map(ColorEffect::getColor)
                .or(() -> Optional.ofNullable(defaultColor))
                .orElseThrow(() -> new IllegalStateException("No active effect found. Please call isActive() first."));
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }
}
