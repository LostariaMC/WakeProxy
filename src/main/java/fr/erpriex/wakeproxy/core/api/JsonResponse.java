package fr.erpriex.wakeproxy.core.api;

import lombok.Getter;

public class JsonResponse {

    @Getter
    private boolean success;

    @Getter
    private String codeMsg;

    @Getter
    private String message;

}
