package fr.erpriex.wakeproxy;

import fr.erpriex.wakeproxy.instances.CurrentInstance;
import fr.erpriex.wakeproxy.listeners.LoginListener;
import fr.erpriex.wakeproxy.listeners.ProxyPingListener;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;

public class WakeProxy extends Plugin {

    @Getter
    @Setter
    private CurrentInstance currentInstance;

    @Override
    public void onEnable() {
        getLogger().info("WakeProxy is starting up!");
        currentInstance = null;

        getProxy().getPluginManager().registerListener(this, new LoginListener(this));
        getProxy().getPluginManager().registerListener(this, new ProxyPingListener());

        getLogger().info("WakeProxy has started successfully!");
    }

}
