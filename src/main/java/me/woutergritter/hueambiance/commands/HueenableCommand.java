package me.woutergritter.hueambiance.commands;

import org.bukkit.entity.Player;

import java.util.List;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static org.bukkit.ChatColor.*;

public class HueenableCommand extends Command {

    public HueenableCommand() {
        super("hueenable");
    }

    @Override
    public void execute(CommandContext context) {
        Player player = context.getSenderAsPlayer().orElseThrow();

        String arg = context.getArgument(0).orElse(null);

        boolean currentEnabled = getPlugin().getHueManager().isEnabledFor(player);
        if (arg == null) {
            context.sendMessage(GREEN + "Hue ambiance is currently " + YELLOW + (currentEnabled ? "enabled" : "disabled") + GREEN + " for you. Type " + YELLOW + "/enablehue " + (currentEnabled ? "disable" : "enable") + GREEN + " to toggle this.");
            return;
        }

        boolean enabled;
        switch (arg) {
            case "enable" -> enabled = true;
            case "disable" -> enabled = false;
            default -> {
                context.sendMessage("Usage: /enablehue <enable | disable>", RED);
                return;
            }
        }

        if (enabled == currentEnabled) {
            context.sendMessage(GREEN + "Hue ambiance is already " + YELLOW + (currentEnabled ? "enabled" : "disabled") + GREEN + " for you.");
            return;
        }

        getPlugin().getHueManager().setEnabledFor(player, enabled);
        getPlugin().getColorManager().reloadBridge(player);
        context.sendMessage(GREEN + "Hue ambiance is now " + YELLOW + (enabled ? "enabled" : "disabled") + GREEN + " for you.");
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return List.of("enable", "disable");
    }
}
