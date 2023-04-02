package pl.szczurowsky.minecraftsentryreporter.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.szczurowsky.minecraftsentryreporter.common.MSRPlugin;
import pl.szczurowsky.minecraftsentryreporter.common.config.ConfigurationManager;
import pl.szczurowsky.minecraftsentryreporter.common.logging.SentryLogHandler;
import pl.szczurowsky.minecraftsentryreporter.common.sentry.SentryInitializer;
import pl.szczurowsky.minecraftsentryreporter.spigot.command.ProductionSwitchCommand;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Handler;

public class MSRSpigotPlugin extends JavaPlugin implements MSRPlugin {

    private ConfigurationManager configurationManager;
    private Handler logHandler;

    @Override
    public void onEnable() {

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadConfigurations();
        try {
            SentryInitializer.initSentry(configurationManager.getSentryConfig().getDsn());
        } catch (Exception e) {
            Bukkit.getLogger().log(java.util.logging.Level.SEVERE, "Error while initializing Sentry", e);
            Bukkit.getLogger().info("Check your Sentry DSN in config.yml");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.getCommand("msrproduction").setExecutor(new ProductionSwitchCommand(configurationManager));
        this.logHandler = new SentryLogHandler(configurationManager.getSentryConfig(), this);
        Bukkit.getLogger().getParent().addHandler(logHandler);
    }

    @Override
    public void onDisable() {
        if (logHandler != null) Bukkit.getLogger().getParent().removeHandler(logHandler);
    }

    @Override
    public String getServerName() {
        return this.configurationManager.getSentryConfig().getServerName();
    }

    @Override
    public String getServerVersion() {
        return Bukkit.getBukkitVersion();
    }

    @Override
    public String getServerImplementation() {
        return Bukkit.getServer().getName();
    }

    @Override
    public String getPluginRelease() {
        return this.getDescription().getVersion();
    }

    @Override
    public SortedMap<String, String> getLoadedPlugins() {
        SortedMap<String, String> pluginVersions = new TreeMap<>();
        for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            pluginVersions.put(plugin.getName(), plugin.getDescription().getVersion());
        }
        return pluginVersions;
    }

    @Override
    public int getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().size();
    }
}
