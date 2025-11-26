package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.Duration;
import java.time.Instant;

public class AppController {
    private Calculator calculator;
    private Fetcher fetcher = new Fetcher(Config.API_KEY);

    @FXML
    private Label resultLabel;
    @FXML
    private ComboBox<Currency> currencyBox;
    @FXML
    private ComboBox<Currency> targetCurrencyBox;
    @FXML
    private TextField moneyField;

    @FXML
    public void initialize() {
        System.out.println("Fetching currency rates...");
        JsonObject fetchedCurrencyRates = fetcher.fetchCurrencyRates();
        calculator = new Calculator(
                new CurrencyRates(fetchedCurrencyRates)
        );
        System.out.println("Successfully fetched currency rates!");

        currencyBox.getItems().addAll(Currency.values());
        targetCurrencyBox.getItems().addAll(Currency.values());
        currencyBox.setValue(Currency.EUR);
        targetCurrencyBox.setValue(Currency.USD);
        moneyField.setTextFormatter(new TextFormatter<>(change ->
                change.getText().matches("[0-9.]*") ? change : null
        ));
    }

    @FXML
    protected void onCalculateButtonClick() {
        if (Duration.between(calculator.getCurrencyRates().fetchTime(), Instant.now()).toMinutes() >= 1) {
            System.out.println("Fetching currency rates...");
            calculator.setCurrencyRates(
                    new CurrencyRates(fetcher.fetchCurrencyRates())
            );
            System.out.println("Successfully updated currency rates!");
        }

        String moneyText = moneyField.getText();
        if (moneyText.isBlank()) {
            resultLabel.setText("Please provide an amount");
            return;
        }
        double money = Double.parseDouble(moneyField.getText());
        Currency currency = currencyBox.getValue();
        Currency targetCurrency = targetCurrencyBox.getValue();
        double result = calculator.calculate(money, currency, targetCurrency);
        resultLabel.setText("{money} {currency} is {result} {targetCurrency}"
                .replace("{money}", String.valueOf(money))
                .replace("{currency}", currency.name())
                .replace("{result}", String.valueOf(result))
                .replace("{targetCurrency}", targetCurrency.name())
        );
    }
}
