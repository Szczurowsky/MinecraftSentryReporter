package pl.szczurowsky.minecraftsentryreporter.common.config;

import eu.okaeri.configs.hjson.HjsonConfigurer;
import pl.szczurowsky.minecraftsentryreporter.common.config.file.SentryConfig;

import java.io.File;

/**
 * Configuration manager is used to load all configurations
 * @author Szczurowsky
 */
public class ConfigurationManager {

    private final File configDirectory;

    private SentryConfig sentryConfig;

    public ConfigurationManager(File configDirectory) {
        this.configDirectory = configDirectory;
    }

    public void loadConfigurations() {
        ConfigFactory configFactory = new ConfigFactory(configDirectory, HjsonConfigurer::new);
        this.sentryConfig = configFactory.produceConfig(SentryConfig.class, "sentry.json");
    }

    public SentryConfig getSentryConfig() {
        return sentryConfig;
    }

}
