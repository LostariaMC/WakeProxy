package fr.erpriex.wakeproxy.listeners;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing serverPing = event.getResponse();
        serverPing.setDescription("                         §9§lLostaria§r\n              §6On revient très vite l'équipe !");
        event.setResponse(serverPing);
    }

}
