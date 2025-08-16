package fr.erpriex.wakeproxy.instances;

import lombok.Getter;

public class CurrentInstance {

    @Getter
    private String id;

    @Getter
    private String name;

    public CurrentInstance(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
