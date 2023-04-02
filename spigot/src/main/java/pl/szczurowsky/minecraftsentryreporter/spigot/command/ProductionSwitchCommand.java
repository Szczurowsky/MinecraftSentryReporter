package pl.szczurowsky.minecraftsentryreporter.spigot.command;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
                    sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aProduction mode enabled!"));
                } else if (args[0].equalsIgnoreCase("false")) {
                    configurationManager.getSentryConfig().setProduction(false);
                    sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aProduction mode disabled!"));
                }
                configurationManager.getSentryConfig().save();
                return true;
            }
            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&cUsage: /msrproduction <true|false>"));
            return true;
        }
        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&cYou don't have permission to use this command!"));
        return true;
    }
}
