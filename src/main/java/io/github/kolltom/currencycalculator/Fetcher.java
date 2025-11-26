package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Fetcher {
    private final String API_URI = "https://api.freecurrencyapi.com/v1/latest?apikey={api_key}";

    private final ConfigController configController;

    public Fetcher(ConfigController configController) {
        this.configController = configController;
    }

    public JsonObject fetchCurrencyRates() {
        URI uri = URI.create(API_URI.replace("{api_key}", configController.getApiKey()));
        HttpRequest httpRequest = HttpRequest.newBuilder(uri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            if (!(statusCode >= 200 && statusCode <= 300)) return null;
            return JsonParser.parseString(httpResponse.body()).getAsJsonObject().getAsJsonObject("data");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
