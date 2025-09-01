package fr.erpriex.wakeproxy.core;

import fr.erpriex.wakeproxy.WakeProxy;
import fr.erpriex.wakeproxy.core.api.ApiException;
import fr.erpriex.wakeproxy.core.api.ApiPath;
import fr.erpriex.wakeproxy.core.api.ApiResponse;
import fr.erpriex.wakeproxy.core.api.JsonResponse;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public class InstanceManager {

    private final WakeProxy main;

    @Getter
    @Setter
    private InstanceStatus instanceStatus;

    @Getter
    @Setter
    private boolean minecraftServerRunning;

    @Getter
    private ScheduledTask minecraftCheckTask;

    public InstanceManager(WakeProxy main) {
        this.main = main;

        main.getProxy().getScheduler().schedule(main, this::updateInstanceStatus, 0, 20, TimeUnit.MINUTES);
        minecraftServerRunning = false;
    }

    public void updateInstanceStatus() {
        try {
            ApiResponse response = main.getApiClient().get(ApiPath.INSTANCE_STATUS.getPath());
            JsonResponse jsonResponse = response.toJsonResponse();
            instanceStatus = InstanceStatus.valueOf(jsonResponse.getMessage());

            if((instanceStatus.isStopping() || instanceStatus.isStopped()) && minecraftServerRunning) {
                minecraftServerRunning = false;
            }
        } catch (ApiException e) {
            main.getLogger().severe("Erreur lors de la mise à jour du statut de l'instance: " + e.getMessage());
        }
    }

    public void waitForMinecraftServer() {
        if (minecraftCheckTask != null) {
            main.getLogger().warning("Une vérification du serveur Minecraft est déjà en cours !");
            return;
        }

        minecraftCheckTask = main.getProxy().getScheduler().schedule(main, () -> {
            try {
                ApiResponse response = main.getApiClient().get(ApiPath.MINECRAFT_STATUS.getPath());
                JsonResponse jsonResponse = response.toJsonResponse();

                if (jsonResponse.getMessage().equalsIgnoreCase("true")) {
                    minecraftServerRunning = true;
                    instanceStatus = InstanceStatus.ACTIVE;

                    minecraftCheckTask.cancel();
                    minecraftCheckTask = null;
                }

            } catch (ApiException e) {
                main.getLogger().warning("Erreur lors du check Minecraft: " + e.getMessage());
            }
        }, 0, 20, TimeUnit.SECONDS);
    }
}
