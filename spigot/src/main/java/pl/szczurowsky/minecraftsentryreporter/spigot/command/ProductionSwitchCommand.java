package pl.szczurowsky.minecraftsentryreporter.spigot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import pl.szczurowsky.minecraftsentryreporter.common.config.ConfigurationManager;

public class ProductionSwitchCommand implements CommandExecutor {

    private final ConfigurationManager configurationManager;

    public ProductionSwitchCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((sender instanceof ConsoleCommandSender) || sender.hasPermission("msr.switchproduction")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("true")) {
                    configurationManager.getSentryConfig().setProduction(true);
                    sender.sendMessage(translateColorCodes("&aProduction mode enabled!"));
                } else if (args[0].equalsIgnoreCase("false")) {
                    configurationManager.getSentryConfig().setProduction(false);
                    sender.sendMessage(translateColorCodes("&aProduction mode disabled!"));
                }
                configurationManager.getSentryConfig().save();
            }
            sender.sendMessage(translateColorCodes("&cUsage: /msrproduction <true|false>"));
        }
        sender.sendMessage(translateColorCodes("&cYou don't have permission to use this command!"));
        return true;
    }

    private static String translateColorCodes(String message) {
        return message.replace("&", "§");
    }

}
