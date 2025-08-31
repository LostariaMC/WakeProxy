package fr.erpriex.wakeproxy.core.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    private final String baseUrl;
    private final String token;

    public ApiClient(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    public ApiResponse get(String endpoint) throws ApiException {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", token);

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    (status >= 200 && status < 300)
                            ? conn.getInputStream()
                            : conn.getErrorStream(),
                    StandardCharsets.UTF_8
            ));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            conn.disconnect();

            if (status >= 200 && status < 300) {
                return new ApiResponse(status, response.toString());
            } else {
                throw new ApiException("Erreur API (" + status + "): " + response);
            }

        } catch (Exception e) {
            throw new ApiException("Erreur lors de l'appel API", e);
        }
    }

    public ApiResponse post(String endpoint) throws ApiException {
        return doPost(endpoint, null);
    }

    public ApiResponse post(String endpoint, String jsonBody) throws ApiException {
        return doPost(endpoint, jsonBody);
    }

    private ApiResponse doPost(String endpoint, String jsonBody) throws ApiException {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", token);

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // Envoi du body uniquement s'il est présent
            if (jsonBody != null && !jsonBody.isBlank()) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int status = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    (status >= 200 && status < 300)
                            ? conn.getInputStream()
                            : conn.getErrorStream(),
                    StandardCharsets.UTF_8
            ));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            conn.disconnect();

            if (status >= 200 && status < 300) {
                return new ApiResponse(status, response.toString());
            } else {
                throw new ApiException("Erreur API (" + status + "): " + response);
            }

        } catch (Exception e) {
            throw new ApiException("Erreur lors de l'appel API", e);
        }
    }
}
