package io.github.kolltom.currencycalculator;

public class Calculator {
    private CurrencyRates currencyRates;

    public Calculator(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }

    public double calculate(double money, Currency currency, Currency targetCurrency) {
        double currencyValue = currencyRates.getByCode(currency);
        double targetCurrencyValue = currencyRates.getByCode(targetCurrency);
        double multiplier = 1 / currencyValue;
        return targetCurrencyValue * multiplier * money;
    }

    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }
}
