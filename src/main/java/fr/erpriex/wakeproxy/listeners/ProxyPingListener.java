package fr.erpriex.wakeproxy.listeners;

import fr.erpriex.wakeproxy.WakeProxy;
import fr.erpriex.wakeproxy.core.InstanceStatus;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

    private WakeProxy main;

    public ProxyPingListener(WakeProxy main) {
        this.main = main;
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing serverPing = event.getResponse();
        serverPing.setDescription(getDescription());
        event.setResponse(serverPing);
    }

    private String getDescription() {
        return switch (main.getInstanceManager().getInstanceStatus()) {
            case SHELVED_OFFLOADED -> "             §9§lLostaria§7  -  §c❌ Serveur éteint ❌\n   §eConnecte toi pour démarrer une instance de jeu";
            case ACTIVE -> "           §9§lLostaria§7  -  §a✔ Serveur démarré ✔\n           §eConnecte toi sur §b§lgame.lostaria.fr§r";
            case UNSHELVING -> "      §9§lLostaria§7  -  §e⧖ En cours de démarrage ⧖\n §ePrépare toi à te connecter sur §b§lgame.lostaria.fr§r";
            case SHELVING -> "           §9§lLostaria§7  -  §e⧖ En cours d'arrêt ⧖\n               §ePatiente quelques instants...";
            default -> "                         §9§lLostaria§r\n               §cUne erreur est survenue";
        };
    }

}
