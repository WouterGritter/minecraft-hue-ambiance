package me.woutergritter.hueambiance.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Command implements CommandExecutor {

    private final String command;
    private final boolean runInThread;

    public Command(String command, boolean runInThread) {
        this.command = command;
        this.runInThread = runInThread;
    }

    public abstract void execute(CommandContext context);

    public void executeSafely(CommandContext context) {
        try {
            execute(context);
        } catch (Exception e) {
            e.printStackTrace();

            context.sendMessage("An exception occurred when attempting to execute the command: " + e, ChatColor.RED);
            context.sendMessage("See the console for more information.", ChatColor.RED);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        var context = new CommandContext(sender, args);

        if (runInThread) {
            new Thread(() -> executeSafely(context)).start();
        } else {
            executeSafely(context);
        }

        return true;
    }

    public void register(JavaPlugin plugin) {
        PluginCommand cmd = plugin.getCommand(command);
        if (cmd == null) {
            throw new IllegalStateException("Command not found.");
        }

        cmd.setExecutor(this);
    }
}
