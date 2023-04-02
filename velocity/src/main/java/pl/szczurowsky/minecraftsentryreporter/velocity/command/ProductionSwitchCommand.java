package pl.szczurowsky.minecraftsentryreporter.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import pl.szczurowsky.minecraftsentryreporter.common.config.ConfigurationManager;

public class ProductionSwitchCommand implements SimpleCommand {

    private final ConfigurationManager configurationManager;

    public ProductionSwitchCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("true")) {
                configurationManager.getSentryConfig().setProduction(true);
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aProduction mode enabled!"));
            } else if (args[0].equalsIgnoreCase("false")) {
                configurationManager.getSentryConfig().setProduction(false);
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aProduction mode disabled!"));
            }
            configurationManager.getSentryConfig().save();
            return;
        }
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&cUsage: /msrproduction <true|false>"));
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("msr.production");
    }

}
