package fr.erpriex.wakeproxy.core.api;

public enum ApiPath {
    PUBLIC_PING("/public/ping");

    private final String path;

    ApiPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
