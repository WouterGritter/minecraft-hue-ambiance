package me.woutergritter.hueambiance.commands;


import io.github.zeroone3010.yahueapi.HueBridge;
import me.woutergritter.hueambiance.hue.HueCredentials;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

public class HueconnectCommand extends Command {

    public HueconnectCommand() {
        super("hueconnect", true);
    }

    @Override
    public void execute(CommandContext context) {
        String hueAddress = context.getArgument(0)
                .or(() -> discoverHueAddress(context))
                .orElse(null);

        if (hueAddress == null) {
            return;
        }

        context.sendMessage("Please click on the button on the Hue bridge to connect it.", GREEN);

        String apiKey = getPlugin()
                .getHueManager()
                .obtainApiKey(hueAddress)
                .orElse(null);

        if (apiKey == null) {
            context.sendMessage("Could not obtain API key.", RED);
            return;
        }

        getPlugin().getHueManager().storeCredentials(new HueCredentials(hueAddress, apiKey));
        context.sendMessage("Stored credentials. Connecting to Hue bridge..", GREEN);

        boolean success = getPlugin().getHueManager().connect();
        if (success) {
            Bukkit.getOnlinePlayers().forEach(getPlugin().getColorManager()::reloadBridge);
            context.sendMessage("Connected to Hue bridge.", GREEN);
        } else {
            context.sendMessage("Could not connect to Hue bridge.", RED);
        }
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return Collections.emptyList();
    }

    private Optional<String> discoverHueAddress(CommandContext context) {
        context.sendMessage("Looking for hue bridges on the network...", GREEN);

        var bridgeFuture = getPlugin()
                .getHueManager()
                .discoverBridge();

        HueBridge bridge;
        try {
            bridge = bridgeFuture.get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            context.sendMessage("Could not find any hue bridge.", RED);
            return Optional.empty();
        }

        context.sendMessage("Found bridge '" + bridge.getName() + "' on " + bridge.getIp(), GREEN);
        return Optional.of(bridge.getIp());
    }
}
