package io.github.kolltom.currencycalculator;

public class Calculator {
    private final CurrencyRates currencyRates;

    public Calculator(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }

    public double calculate(double money, String currency, String targetCurrency) {
        double currencyValue = currencyRates.getCurrencyRate(currency);
        double targetCurrencyValue = currencyRates.getCurrencyRate(targetCurrency);
        double multiplier = 1 / currencyValue;
        return targetCurrencyValue * multiplier * money;
    }

    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }
}
