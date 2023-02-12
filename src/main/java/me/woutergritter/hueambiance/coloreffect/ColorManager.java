package me.woutergritter.hueambiance.coloreffect;

import me.woutergritter.hueambiance.coloreffect.effects.ColorEffect;
import me.woutergritter.hueambiance.coloreffect.effects.CompositeColorEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static me.woutergritter.hueambiance.coloreffect.ColorEffectHelpers.instantiateRegisteredColorEffects;
import static org.bukkit.Bukkit.getPluginManager;

public class ColorManager implements Listener {

    private final Map<Player, PlayerColorEffectBridge> bridges = new HashMap<>();

    public ColorManager() {
        getPluginManager().registerEvents(this, getPlugin());

        Bukkit.getOnlinePlayers().forEach(this::setupBridge);
    }

    private void setupBridge(Player player) {
        if (!getPlugin().getHueManager().canSetStateFor(player)) {
            return;
        }

        ColorEffect effect = new CompositeColorEffect(
                Color.BLACK,
                instantiateRegisteredColorEffects(player)
        );
        var bridge = new PlayerColorEffectBridge(effect, player);

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
}
