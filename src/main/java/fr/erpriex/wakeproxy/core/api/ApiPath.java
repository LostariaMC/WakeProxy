package fr.erpriex.wakeproxy.core.api;

public enum ApiPath {
    INSTANCE_STOP("/instance/stop"),
    INSTANCE_START("/instance/start"),
    INSTANCE_STATUS("/instance/status"),
    PUBLIC_PING("/public/ping"),
    MINECRAFT_STATUS("/minecraft/status"),
    MINECRAFT_PLAYERS("/minecraft/players");

    private final String path;

    ApiPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
