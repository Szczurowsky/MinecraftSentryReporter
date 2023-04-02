package pl.szczurowsky.minecraftsentryreporter.common;

import java.util.SortedMap;

public interface MSRPlugin {

    String getServerName();
    String getPluginRelease();
    String getServerVersion();
    String getServerImplementation();
    SortedMap<String, String> getLoadedPlugins();
    int getOnlinePlayers();

}
