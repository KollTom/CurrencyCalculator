package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;

import java.time.Instant;

public record CurrencyRates(
        Instant fetchTime, double AUD, double BGN, double BRL, double CAD, double CHF,
        double CNY, double CZK, double DKK, double EUR, double GBP,
        double HKD, double HRK, double HUF, double IDR, double ILS,
        double INR, double ISK, double JPY, double KRW, double MXN,
        double MYR, double NOK, double NZD, double PHP, double PLN,
        double RON, double RUB, double SEK, double SGD, double THB,
        double TRY, double USD, double ZAR
) {
    public CurrencyRates(JsonObject currencyRatesJsonValue) {
        this(
                Instant.now(),
                currencyRatesJsonValue.get("AUD").getAsDouble(),
                currencyRatesJsonValue.get("BGN").getAsDouble(),
                currencyRatesJsonValue.get("BRL").getAsDouble(),
                currencyRatesJsonValue.get("CAD").getAsDouble(),
                currencyRatesJsonValue.get("CHF").getAsDouble(),
                currencyRatesJsonValue.get("CNY").getAsDouble(),
                currencyRatesJsonValue.get("CZK").getAsDouble(),
                currencyRatesJsonValue.get("DKK").getAsDouble(),
                currencyRatesJsonValue.get("EUR").getAsDouble(),
                currencyRatesJsonValue.get("GBP").getAsDouble(),
                currencyRatesJsonValue.get("HKD").getAsDouble(),
                currencyRatesJsonValue.get("HRK").getAsDouble(),
                currencyRatesJsonValue.get("HUF").getAsDouble(),
                currencyRatesJsonValue.get("IDR").getAsDouble(),
                currencyRatesJsonValue.get("ILS").getAsDouble(),
                currencyRatesJsonValue.get("INR").getAsDouble(),
                currencyRatesJsonValue.get("ISK").getAsDouble(),
                currencyRatesJsonValue.get("JPY").getAsDouble(),
                currencyRatesJsonValue.get("KRW").getAsDouble(),
                currencyRatesJsonValue.get("MXN").getAsDouble(),
                currencyRatesJsonValue.get("MYR").getAsDouble(),
                currencyRatesJsonValue.get("NOK").getAsDouble(),
                currencyRatesJsonValue.get("NZD").getAsDouble(),
                currencyRatesJsonValue.get("PHP").getAsDouble(),
                currencyRatesJsonValue.get("PLN").getAsDouble(),
                currencyRatesJsonValue.get("RON").getAsDouble(),
                currencyRatesJsonValue.get("RUB").getAsDouble(),
                currencyRatesJsonValue.get("SEK").getAsDouble(),
                currencyRatesJsonValue.get("SGD").getAsDouble(),
                currencyRatesJsonValue.get("THB").getAsDouble(),
                currencyRatesJsonValue.get("TRY").getAsDouble(),
                currencyRatesJsonValue.get("USD").getAsDouble(),
                currencyRatesJsonValue.get("ZAR").getAsDouble()
        );
    }

    public double getByCode(Currency code) {
        return switch (code) {
            case AUD -> AUD;
            case BGN -> BGN;
            case BRL -> BRL;
            case CAD -> CAD;
            case CHF -> CHF;
            case CNY -> CNY;
            case CZK -> CZK;
            case DKK -> DKK;
            case EUR -> EUR;
            case GBP -> GBP;
            case HKD -> HKD;
            case HRK -> HRK;
            case HUF -> HUF;
            case IDR -> IDR;
            case ILS -> ILS;
            case INR -> INR;
            case ISK -> ISK;
            case JPY -> JPY;
            case KRW -> KRW;
            case MXN -> MXN;
            case MYR -> MYR;
            case NOK -> NOK;
            case NZD -> NZD;
            case PHP -> PHP;
            case PLN -> PLN;
            case RON -> RON;
            case RUB -> RUB;
            case SEK -> SEK;
            case SGD -> SGD;
            case THB -> THB;
            case TRY -> TRY;
            case USD -> USD;
            case ZAR -> ZAR;
        };
    }


}

