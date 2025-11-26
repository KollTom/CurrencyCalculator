package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CurrencyRates {
    private final Instant fetchTime;
    private final Map<String, Double> currencyRates;

    public CurrencyRates(JsonObject currencyRatesJsonValue) {
        this.fetchTime = Instant.now();
        this.currencyRates = currencyRatesJsonValue.asMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, entry -> entry.getValue().getAsDouble()
                ));
    }

    public Instant getFetchTime() {
        return fetchTime;
    }

    public double getCurrencyRate(String currency) {
        return currencyRates.get(currency);
    }

    public Set<String> getCurrencies() {
        return currencyRates.keySet();
    }
}

