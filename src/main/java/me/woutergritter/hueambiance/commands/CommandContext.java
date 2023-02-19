package me.woutergritter.hueambiance.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class CommandContext {

    private final CommandSender sender;
    private final List<String> arguments;

    public CommandContext(CommandSender sender, String[] arguments) {
        this.sender = sender;
        this.arguments = List.of(arguments);
    }

    public void sendMessage(BaseComponent component) {
        sender.spigot().sendMessage(component);
    }

    public void sendMessage(BaseComponent... component) {
        sender.spigot().sendMessage(component);
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public void sendMessage(String message, ChatColor color) {
        sendMessage(color.toString() + message);
    }

    public CommandSender getSender() {
        return sender;
    }

    public Optional<Player> getSenderAsPlayer() {
        if (!(sender instanceof Player)) {
            return Optional.empty();
        }

        return Optional.of((Player) sender);
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Optional<String> getArgumentsAsString() {
        return Optional.of(String.join(" ", arguments))
                .filter(s -> !s.isBlank());
    }

    public Optional<String> getArgument(int index) {
        if (index < 0 || index >= arguments.size()) {
            return Optional.empty();
        }

        return Optional.of(arguments.get(index));
    }

    public int getArgumentAmount() {
        return arguments.size();
    }

    public boolean hasArguments() {
        return !arguments.isEmpty();
    }
}
