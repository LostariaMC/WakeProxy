package fr.erpriex.wakeproxy.listeners;

import fr.erpriex.wakeproxy.WakeProxy;
import fr.erpriex.wakeproxy.core.api.ApiException;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;

public class PreLoginListener implements Listener {

    private WakeProxy main;

    public PreLoginListener(WakeProxy main) {
        this.main = main;
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent event) throws ApiException {
        String ip = ((InetSocketAddress) event.getConnection().getSocketAddress()).getAddress().getHostAddress();

        if(!main.getIpManager().checkIp(ip)) {
            main.getLogger().info("IP " + event.getConnection().getAddress().getAddress().getHostAddress() + " is not authorized");
            event.setCancelReason("§9§lLostaria§r\n\n\n§cVotre région est refusée\n\n§ePlus d'informations sur §b§ldiscord.lostaria.fr§r\n");
            event.setCancelled(true);
        }
    }

}
