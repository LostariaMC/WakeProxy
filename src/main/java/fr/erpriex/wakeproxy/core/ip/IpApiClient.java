package fr.erpriex.wakeproxy.core.ip;

import com.google.gson.Gson;
import fr.erpriex.wakeproxy.core.api.ApiClient;
import fr.erpriex.wakeproxy.core.api.ApiException;
import fr.erpriex.wakeproxy.core.api.ApiResponse;

public class IpApiClient {

    private static final String BASE_URL = "http://ip-api.com";
    private final ApiClient apiClient;
    private final Gson gson = new Gson();

    public IpApiClient() {
        this.apiClient = new ApiClient(BASE_URL, null);
    }

    public IpApiResponse getIpInfo(String ip) throws ApiException {
        String endpoint = "/json/";

        if (ip != null && !ip.isBlank()) {
            endpoint += ip;
        }

        ApiResponse response = apiClient.get(endpoint);

        return gson.fromJson(response.getBody(), IpApiResponse.class);
    }
}
