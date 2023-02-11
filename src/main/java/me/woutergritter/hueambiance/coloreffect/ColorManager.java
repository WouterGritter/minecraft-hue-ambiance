package me.woutergritter.hueambiance.coloreffect;

import me.woutergritter.hueambiance.coloreffect.effects.ColorEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getScheduler;

public class ColorManager implements Listener {

    private final Map<Player, PlayerColorEffectBridge> bridges = new HashMap<>();

    public ColorManager() {
        getPluginManager().registerEvents(this, getPlugin());

        Bukkit.getOnlinePlayers().forEach(this::setupBridge);

        getScheduler().runTaskTimer(getPlugin(), this::update, 1, 1);
    }

    public void update() {
        bridges.values().forEach(PlayerColorEffectBridge::update);
    }

    private void setupBridge(Player player) {
        if (!isColorsEnabledFor(player)) {
            return;
        }

        var effects = findRegisteredColorEffectClasses();
        var bridge = new PlayerColorEffectBridge(effects, player);
        bridge.setup();
        bridges.put(player, bridge);
    }

    private void disposeBridge(Player player) {
        var bridge = bridges.remove(player);
        if (bridge != null) {
            bridge.dispose();
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        setupBridge(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        disposeBridge(e.getPlayer());
    }

    private boolean isColorsEnabledFor(Player player) {
        // TODO: Add support for multiple players.
        return player.getName().equals("TheWGBbroz");
    }

    private Collection<Class<? extends ColorEffect>> findRegisteredColorEffectClasses() {
        return new Reflections("me.woutergritter.hueambiance.coloreffect.effects")
                .getTypesAnnotatedWith(RegisteredColorEffect.class)
                .stream()
                .filter(ColorEffect.class::isAssignableFrom)
                .map(clazz -> (Class<? extends ColorEffect>) clazz)
                .collect(Collectors.toSet());
    }
}
