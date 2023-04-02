package pl.szczurowsky.minecraftsentryreporter.common.sentry;

import io.sentry.Sentry;

public class SentryInitializer {

    public static void initSentry(String dsn) {
        Sentry.init(options -> {
            options.setDsn(dsn);
            options.setDebug(false);
            options.setShutdownTimeoutMillis(5000);
        });
    }

}
