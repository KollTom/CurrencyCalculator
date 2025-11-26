package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Fetcher {
    private final URI uri;

    public Fetcher(String api_token) {
        String API_URI = "https://api.freecurrencyapi.com/v1/latest?apikey={api_key}";
        this.uri = URI.create(API_URI.replace("{api_key}", api_token));

    }

    public JsonObject fetchCurrencyRates() {
        HttpRequest httpRequest = HttpRequest.newBuilder(uri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return JsonParser.parseString(httpResponse.body()).getAsJsonObject().getAsJsonObject("data");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
