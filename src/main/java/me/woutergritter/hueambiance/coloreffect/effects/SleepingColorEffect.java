package me.woutergritter.hueambiance.coloreffect.effects;

import me.woutergritter.hueambiance.coloreffect.RegisteredColorEffect;
import org.bukkit.entity.Player;

import java.awt.*;

@RegisteredColorEffect
public class SleepingColorEffect implements ColorEffect {

    private final Player player;

    public SleepingColorEffect(Player player) {
        this.player = player;
    }

    @Override
    public boolean isActive() {
        return player.isSleeping();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public int getPriority() {
        return 19;
    }
}
