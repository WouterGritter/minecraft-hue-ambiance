package me.woutergritter.hueambiance.coloreffect.effects;

import java.awt.*;

public class ColorRandomizer {

    private final Color min, max;
    private final long changeInterval;

    private Color color;
    private long lastChange;

    public ColorRandomizer(Color a, Color b, long changeInterval) {
        this.min = new Color(
                Math.min(a.getRed(), b.getRed()),
                Math.min(a.getGreen(), b.getGreen()),
                Math.min(a.getBlue(), b.getBlue())
        );

        this.max = new Color(
                Math.max(a.getRed(), b.getRed()),
                Math.max(a.getGreen(), b.getGreen()),
                Math.max(a.getBlue(), b.getBlue())
        );

        this.changeInterval = changeInterval;
    }

    public Color getColor() {
        long now = System.currentTimeMillis();
        if (color == null || now - lastChange > changeInterval) {
            color = getRandomColor();
            lastChange = now;
        }

        return color;
    }

    private Color getRandomColor() {
        return new Color(
                random(min.getRed(), max.getRed()),
                random(min.getGreen(), max.getGreen()),
                random(min.getBlue(), max.getBlue())
        );
    }

    private static int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
