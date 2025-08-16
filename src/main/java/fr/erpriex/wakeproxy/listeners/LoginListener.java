package fr.erpriex.wakeproxy.listeners;

import fr.erpriex.wakeproxy.WakeProxy;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

    private WakeProxy main;

    public LoginListener(WakeProxy main) {
        this.main = main;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        event.setCancelReason(new TextComponent("§9§lLostaria§r\n\n\n§eOn est au charbon, on revient très vite !\n\n§e§oMais promis, on pense fort à toi,\n§e§oon te fait des gros bisous !§r\n"));
        event.setCancelled(true);
    }

}
