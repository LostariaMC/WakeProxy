package fr.erpriex.wakeproxy;

import fr.erpriex.wakeproxy.core.InstanceManager;
import fr.erpriex.wakeproxy.core.api.ApiClient;
import fr.erpriex.wakeproxy.core.api.ApiException;
import fr.erpriex.wakeproxy.core.api.ApiPath;
import fr.erpriex.wakeproxy.core.api.ApiResponse;
import fr.erpriex.wakeproxy.core.ip.IpManager;
import fr.erpriex.wakeproxy.listeners.LoginListener;
import fr.erpriex.wakeproxy.listeners.ProxyPingListener;
import fr.erpriex.wakeproxy.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.logging.Level;

public class WakeProxy extends Plugin {

    @Getter
    @Setter
    private InstanceManager instanceManager;

    @Getter
    private ApiClient apiClient;

    @Getter
    private IpManager ipManager;

    @Getter
    private String apiBaseUrl;

    @Getter
    private String apiToken;

    @Override
    public void onEnable() {
        getLogger().info("WakeProxy is starting up!");

        loadConfig();

        apiClient = new ApiClient(apiBaseUrl, apiToken);
        ipManager = new IpManager();

        try {
            ApiResponse responsePing = apiClient.get(ApiPath.PUBLIC_PING.getPath());
            if (responsePing.getStatus() != 200) {
                getLogger().severe("Unable to ping API: " + responsePing.getBody());
                throw new RuntimeException("Unable to ping API");
            }
            getLogger().info("Pinged API: " + responsePing.getBody());
            getLogger().info("API ping successful!");
        } catch (ApiException e) {
            getLogger().severe("Unable to ping API: " + e.getMessage());
            throw new RuntimeException(e);
        }

        instanceManager = new InstanceManager(this);

        getProxy().getPluginManager().registerListener(this, new LoginListener(this));
        getProxy().getPluginManager().registerListener(this, new ProxyPingListener(this));

        getLogger().info("WakeProxy has started successfully!");
    }

    private void loadConfig() {
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists() && !dataFolder.mkdirs()) {
                getLogger().severe("Unable to create plugin data folder: " + dataFolder.getAbsolutePath());
                return;
            }

            File configFile = new File(dataFolder, "config.yml");

            if (!configFile.exists()) {
                try (InputStream in = getResourceAsStream("config.yml")) {
                    if (in == null) {
                        getLogger().severe("Default config.yml not found in resources!");
                        return;
                    }
                    Files.copy(in, configFile.toPath());
                    getLogger().info("Default config.yml created.");
                }
            }

            Yaml yaml = new Yaml();
            Map<String, Object> root;
            try (InputStream input = Files.newInputStream(configFile.toPath())) {
                root = yaml.load(input);
            }

            if (root == null) {
                throw new IllegalStateException("config.yml is empty or invalid YAML");
            }

            Object apiSectionObj = root.get("api");
            if (!(apiSectionObj instanceof Map)) {
                throw new IllegalStateException("Missing 'api' section in config.yml");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> apiSection = (Map<String, Object>) apiSectionObj;

            this.apiBaseUrl = StringUtils.stringOrNull(apiSection.get("base-url"));
            this.apiToken   = StringUtils.stringOrNull(apiSection.get("token"));

            if (this.apiBaseUrl == null || this.apiBaseUrl.isBlank()) {
                getLogger().severe("Missing 'api.base-url' in config.yml");
            }
            if (this.apiToken == null || this.apiToken.isBlank()) {
                getLogger().warning("Missing 'api.token' in config.yml (continuing, but API may reject requests)");
            }

            getLogger().info("Config loaded. Base URL = " + this.apiBaseUrl);

        } catch (IOException | RuntimeException e) {
            getLogger().log(Level.SEVERE, "Failed to load config.yml", e);
        }
    }

}
