package fr.erpriex.wakeproxy.core.api;

import com.google.gson.Gson;
import lombok.Getter;

public class ApiResponse {

    @Getter
    private final int status;

    @Getter
    private final String body;

    private final Gson gson = new Gson();

    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public JsonResponse toJsonResponse() {
        return gson.fromJson(this.body, JsonResponse.class);
    }
}
