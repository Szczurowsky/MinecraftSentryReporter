package pl.szczurowsky.minecraftsentryreporter.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import pl.szczurowsky.minecraftsentryreporter.bungee.command.ProductionSwitchCommand;
import pl.szczurowsky.minecraftsentryreporter.common.MSRPlugin;
import pl.szczurowsky.minecraftsentryreporter.common.config.ConfigurationManager;
import pl.szczurowsky.minecraftsentryreporter.common.logging.SentryLogHandler;
import pl.szczurowsky.minecraftsentryreporter.common.sentry.SentryInitializer;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Handler;

public class MSRBungeePlugin extends Plugin implements MSRPlugin {

    private ConfigurationManager configurationManager;
    private Handler logHandler;

    @Override
    public void onEnable() {
        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadConfigurations();
        try {
            SentryInitializer.initSentry(configurationManager.getSentryConfig().getDsn());
        } catch (Exception e) {
            this.getProxy().getLogger().log(java.util.logging.Level.SEVERE, "Error while initializing Sentry", e);
            this.getProxy().getLogger().info("Check your Sentry DSN in config.yml");
            return;
        }
        this.getProxy().getPluginManager().registerCommand(this, new ProductionSwitchCommand(configurationManager));
        this.logHandler = new SentryLogHandler(configurationManager.getSentryConfig(), this);
        this.getProxy().getLogger().getParent().addHandler(logHandler);
    }

    @Override
    public void onDisable() {
        if (logHandler != null) this.getProxy().getLogger().getParent().removeHandler(logHandler);
    }

    @Override
    public String getServerName() {
        return this.configurationManager.getSentryConfig().getServerName();
    }

    @Override
    public String getPluginRelease() {
        return this.getDescription().getVersion();
    }

    @Override
    public String getServerVersion() {
        return this.getProxy().getVersion();
    }

    @Override
    public String getServerImplementation() {
        return this.getProxy().getName();
    }

    @Override
    public SortedMap<String, String> getLoadedPlugins() {
        SortedMap<String, String> pluginVersions = new TreeMap<>();
        for(Plugin plugin : this.getProxy().getPluginManager().getPlugins()) {
            pluginVersions.put(plugin.getDescription().getName(), plugin.getDescription().getVersion());
        }
        return pluginVersions;
    }

    @Override
    public int getOnlinePlayers() {
        return this.getProxy().getOnlineCount();
    }
}
