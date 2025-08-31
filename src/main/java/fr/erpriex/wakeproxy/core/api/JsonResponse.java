package fr.erpriex.wakeproxy.core.api;

public class JsonResponse {
    private boolean success;
    private String codeMsg;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public String getMessage() {
        return message;
    }
}
