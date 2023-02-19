package me.woutergritter.hueambiance.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.woutergritter.hueambiance.HueAmbiance.getPlugin;
import static net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND;
import static org.bukkit.ChatColor.*;

public class HueroomCommand extends Command {

    public HueroomCommand() {
        super("hueroom");
    }

    @Override
    public void execute(CommandContext context) {
        Player player = context.getSenderAsPlayer().orElseThrow();

        String roomName = context.getArgumentsAsString().orElse(null);
        String currentRoomName = getPlugin().getHueManager().getRoomNameFor(player).orElse(null);

        if (roomName == null) {
            if (currentRoomName == null) {
                context.sendMessage(GREEN + "You don't have a room linked. Link a room with " + YELLOW + "/hueroom <roomname>" + GREEN + ".");
            } else {
                context.sendMessage(GREEN + "You currently have the room " + YELLOW + currentRoomName + GREEN + " linked. Link a different room with /hueroom <roomname>.");
            }

            sendAvailableRooms(context);
            return;
        }

        if (getPlugin().getHueManager().getAvailableRoomNames().noneMatch(roomName::equals)) {
            context.sendMessage("Invalid room name!", RED);
            sendAvailableRooms(context);
            return;
        }

        if (roomName.equals(currentRoomName)) {
            context.sendMessage(GREEN + "You already have the room " + YELLOW + currentRoomName + GREEN + " linked.");
            return;
        }

        getPlugin().getHueManager().setRoomNameFor(player, roomName);
        getPlugin().getColorManager().reloadBridge(player);
        context.sendMessage(GREEN + "You now have the room " + YELLOW + roomName + GREEN + " linked.");
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return getPlugin().getHueManager().getAvailableRoomNames()
                .filter(s -> s.toLowerCase().startsWith(context.getArgumentsAsString().orElse("").toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    private void sendAvailableRooms(CommandContext context) {
        if (getPlugin().getHueManager().getAvailableRoomNames().findAny().isEmpty()) {
            context.sendMessage(RED + "Could not find any available rooms. Please check if the Hue hub connection is configured correctly.");
            return;
        }

        var message = Stream.concat(
                Stream.of(new TextComponent(GREEN + "Available rooms: " + GRAY + "(click to configure) ")),
                getPlugin().getHueManager().getAvailableRoomNames()
                        .map(name -> {
                            var component = new TextComponent(YELLOW + name);
                            component.setClickEvent(new ClickEvent(RUN_COMMAND, "/hueroom " + name));
                            return component;
                        })
                        .flatMap(c -> Stream.of(new TextComponent(GREEN + ", "), c))
                        .skip(1)
        ).toArray(TextComponent[]::new);

        context.sendMessage(message);
    }
}
