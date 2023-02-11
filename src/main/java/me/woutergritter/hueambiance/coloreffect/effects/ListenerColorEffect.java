package me.woutergritter.hueambiance.coloreffect.effects;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static org.bukkit.Bukkit.getPluginManager;

public abstract class ListenerColorEffect implements ColorEffect, Listener {

    @Override
    public void setup() {
        getPluginManager().registerEvents(this, getPlugin());
    }

    @Override
    public void dispose() {
        HandlerList.unregisterAll(this);
    }
}
