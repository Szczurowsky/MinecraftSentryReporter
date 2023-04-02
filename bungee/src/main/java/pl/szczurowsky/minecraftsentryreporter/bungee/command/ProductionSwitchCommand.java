package pl.szczurowsky.minecraftsentryreporter.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.szczurowsky.minecraftsentryreporter.common.config.ConfigurationManager;

public class ProductionSwitchCommand extends Command {

    private final ConfigurationManager configurationManager;

    public ProductionSwitchCommand(ConfigurationManager configurationManager) {
        super("msrproduction");
        this.configurationManager = configurationManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer) || sender.hasPermission("msr.switchproduction")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("true")) {
                    configurationManager.getSentryConfig().setProduction(true);
                    sender.sendMessage(TextComponent.fromLegacyText(translateColorCodes("&aProduction mode enabled!")));
                } else if (args[0].equalsIgnoreCase("false")) {
                    configurationManager.getSentryConfig().setProduction(false);
                    sender.sendMessage(TextComponent.fromLegacyText(translateColorCodes("&aProduction mode disabled!")));
                }
                configurationManager.getSentryConfig().save();
            }
            sender.sendMessage(TextComponent.fromLegacyText(translateColorCodes("&cUsage: /msrproduction <true|false>")));
        }
        sender.sendMessage(TextComponent.fromLegacyText(translateColorCodes("&cYou don't have permission to use this command!")));
    }


    private static String translateColorCodes(String message) {
        return message.replace("&", "§");
    }

}
