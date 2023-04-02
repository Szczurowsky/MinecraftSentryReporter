package pl.szczurowsky.minecraftsentryreporter.common.config.file;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class SentryConfig extends OkaeriConfig {

    private String dsn = "https://sentry.io/your-dsn";
    private boolean production = false;
    private String serverName = "minecraft-server";

    public String getDsn() {
        return dsn;
    }

    public boolean isProduction() {
        return production;
    }

    public String getServerName() {
        return serverName;
    }

    public void setProduction(boolean production) {
        this.production = production;
    }

}
