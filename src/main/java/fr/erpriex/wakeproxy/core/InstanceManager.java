package fr.erpriex.wakeproxy.core;

import fr.erpriex.wakeproxy.WakeProxy;
import lombok.Getter;
import lombok.Setter;

public class InstanceManager {

    private WakeProxy main;

    @Getter
    @Setter
    private InstanceStatus status;

    public InstanceManager(WakeProxy main) {
        this.main = main;
    }

}
