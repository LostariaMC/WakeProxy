package fr.erpriex.wakeproxy.core.api;

import com.google.gson.Gson;

public class ApiResponse {
    private final int status;
    private final String body;
    private final Gson gson = new Gson();

    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public JsonResponse toJsonResponse() {
        return gson.fromJson(this.body, JsonResponse.class);
    }
}
