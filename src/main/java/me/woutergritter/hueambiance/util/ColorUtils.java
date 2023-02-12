package me.woutergritter.hueambiance.util;

import java.awt.*;

public class ColorUtils {

    private ColorUtils() {
    }

    public static Color randomColor(Color min, Color max) {
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
