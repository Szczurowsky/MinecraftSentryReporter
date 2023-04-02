package pl.szczurowsky.minecraftsentryreporter.velocity;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import pl.szczurowsky.minecraftsentryreporter.common.MSRPlugin;
import pl.szczurowsky.minecraftsentryreporter.common.config.ConfigurationManager;
import pl.szczurowsky.minecraftsentryreporter.common.logging.SentryLogHandler;
import pl.szczurowsky.minecraftsentryreporter.common.sentry.SentryInitializer;
import pl.szczurowsky.minecraftsentryreporter.velocity.command.ProductionSwitchCommand;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Handler;
import java.util.logging.Logger;

@Plugin(
        id = "minecraftsentryreporter",
        name = "MinecraftSentryReporter",
        version = "@version@",
        description = "MinecraftSentryReporter",
        authors = {"Szczurowsky"}
)
public class MSRVelocityPlugin implements MSRPlugin {

    private ConfigurationManager configurationManager;
    private Handler logHandler;
    private final Logger logger;
    private final ProxyServer proxy;

    @Inject
    public MSRVelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.logger = logger;
        this.proxy = server;
        this.configurationManager = new ConfigurationManager(dataDirectory.toFile());
        this.configurationManager.loadConfigurations();
        try {
            SentryInitializer.initSentry(configurationManager.getSentryConfig().getDsn());
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error while initializing Sentry", e);
            logger.info("Check your Sentry DSN in config.yml");
            return;
        }
        this.logHandler = new SentryLogHandler(configurationManager.getSentryConfig(), this);
        logger.getParent().addHandler(logHandler);
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onProxyInitialization(ProxyInitializeEvent event) {
        CommandManager commandManager = proxy.getCommandManager();
        CommandMeta meta = commandManager.metaBuilder("msrproduction").build();
        commandManager.register(meta, new ProductionSwitchCommand(configurationManager));
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if (logHandler != null) logger.getParent().removeHandler(logHandler);
    }

    @Override
    public String getServerName() {
        return this.configurationManager.getSentryConfig().getServerName();
    }

    @Override
    public String getPluginRelease() {
        return "@version@";
    }

    @Override
    public String getServerVersion() {
        return this.proxy.getVersion().toString();
    }

    @Override
    public String getServerImplementation() {
        return this.proxy.getVersion().getName();
    }

    @Override
    public SortedMap<String, String> getLoadedPlugins() {
        SortedMap<String, String> pluginVersions = new TreeMap<>();
        for(PluginContainer plugin : this.proxy.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getName().isPresent() && plugin.getDescription().getVersion().isPresent()) {
                pluginVersions.put(plugin.getDescription().getName().get(), plugin.getDescription().getVersion().get());
            }
        }
        return pluginVersions;
    }

    @Override
    public int getOnlinePlayers() {
        return this.proxy.getPlayerCount();
    }
}
