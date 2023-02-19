package me.woutergritter.hueambiance.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {

    private final String command;
    private final boolean runInThread;

    public Command(String command, boolean runInThread) {
        this.command = command;
        this.runInThread = runInThread;
    }

    public Command(String command) {
        this(command, false);
    }

    public abstract void execute(CommandContext context);

    public abstract List<String> tabComplete(CommandContext context);

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

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        var context = new CommandContext(sender, args);
        return tabComplete(context);
    }

    public void register(JavaPlugin plugin) {
        PluginCommand cmd = plugin.getCommand(command);
        if (cmd == null) {
            throw new IllegalStateException("Command not found.");
        }

        cmd.setExecutor(this);
        cmd.setTabCompleter(this);
    }
}
