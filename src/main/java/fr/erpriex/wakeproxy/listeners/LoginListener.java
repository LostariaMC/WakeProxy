package fr.erpriex.wakeproxy.listeners;

import fr.erpriex.wakeproxy.WakeProxy;
import fr.erpriex.wakeproxy.core.InstanceStatus;
import fr.erpriex.wakeproxy.core.api.ApiException;
import fr.erpriex.wakeproxy.core.api.ApiPath;
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
        if(!main.getInstanceManager().getInstanceStatus().isStarting()) {
            main.getInstanceManager().updateInstanceStatus();
        }
        String cancelReason = "";
        switch (main.getInstanceManager().getInstanceStatus()) {
            case ACTIVE -> {
                cancelReason = "§9§lLostaria§r\n\n\n§eLe serveur est déjà démarré !\n\n§eConnecte toi sur §b§lgame.lostaria.fr§r\n";
            }
            case SHELVED_OFFLOADED -> {
                cancelReason = "§9§lLostaria§r\n\n\n§eLe serveur est en cours de démarrage !\n\n§ePrépare toi à te connecter sur §b§lgame.lostaria.fr§r\n";
                try {
                    main.getApiClient().post(ApiPath.INSTANCE_START.getPath());
                    main.getInstanceManager().setInstanceStatus(InstanceStatus.UNSHELVING);
                    main.getInstanceManager().waitForMinecraftServer();
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case UNSHELVING -> {
                cancelReason = "§9§lLostaria§r\n\n\n§eLe serveur est déjà en cours de démarrage !\n\n§ePrépare toi à te connecter sur §b§lgame.lostaria.fr§r\n";
            }
            case SHELVING -> {
                cancelReason = "§9§lLostaria§r\n\n\n§eLe serveur était en train de s'arrêter !\n\n§ePatiente un peu avant de relancer une instance\n";
            }
            default -> {
                cancelReason = "§9§lLostaria§r\n\n\n§cUne erreur interne est survenue !\n\n§cMerci de contacter un membre de l'équipe\n";
            }
        }
        event.setCancelReason(cancelReason);
        event.setCancelled(true);
    }

}
