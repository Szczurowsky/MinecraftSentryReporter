package pl.szczurowsky.minecraftsentryreporter.common.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.configurer.Configurer;

import java.io.File;
import java.util.function.Supplier;

/**
 * Class responsible for creating config files
 * @author Szczurowsky
 */
public class ConfigFactory {

    private final File dataFile;
    private final Supplier<Configurer> configurerSupplier;

    public ConfigFactory(File dataFile, Supplier<Configurer> configurerSupplier) {
        this.dataFile = dataFile;
        this.configurerSupplier = configurerSupplier;
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> type, String fileName) {
        return this.produceConfig(type, new File(this.dataFile, fileName));
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> type, File file) {
        return ConfigManager.create(type, initializer -> initializer
                .withConfigurer(this.configurerSupplier.get())
                .withBindFile(file)
                .saveDefaults()
                .load(true));
    }

}
