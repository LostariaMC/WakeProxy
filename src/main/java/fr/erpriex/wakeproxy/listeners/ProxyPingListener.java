package fr.erpriex.wakeproxy.listeners;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing serverPing = event.getResponse();
        serverPing.setDescription("           §9§lLostaria §7- §cAdmins en vacances§r\n                    §6Séminaria en cours        §e§o#Amsterdam");
        event.setResponse(serverPing);
    }

}
