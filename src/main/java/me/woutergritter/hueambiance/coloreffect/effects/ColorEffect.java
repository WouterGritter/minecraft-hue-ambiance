package me.woutergritter.hueambiance.coloreffect.effects;

import java.awt.*;

public interface ColorEffect {

    default void setup() {
    }

    default void dispose() {
    }

    boolean isActive();

    Color getColor();

    int getPriority();
}
