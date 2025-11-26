package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.Duration;
import java.time.Instant;

public class MainController {
    private Calculator calculator;
    private final Fetcher fetcher = new Fetcher(CurrencyCalculatorApplication.configController);

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
        currencyBox.getItems().addAll(Currency.values());
        targetCurrencyBox.getItems().addAll(Currency.values());
        currencyBox.setValue(Currency.EUR);
        targetCurrencyBox.setValue(Currency.USD);
        moneyField.setTextFormatter(new TextFormatter<>(change ->
                change.getText().matches("[0-9.]*") ? change : null
        ));
        System.out.println("Fetching currency rates...");
        JsonObject fetchedCurrencyRates = fetcher.fetchCurrencyRates();
        if (fetchedCurrencyRates == null) {
            System.out.println("Error fetching currency rates. Check your API Key!");
        } else {
            calculator = new Calculator(
                    new CurrencyRates(fetchedCurrencyRates)
            );
            System.out.println("Successfully fetched currency rates!");
        }
    }

    @FXML
    protected void onCalculateButtonClick() {
        if (calculator == null || Duration.between(calculator.getCurrencyRates().fetchTime(), Instant.now()).toMinutes() >= 10) {
            System.out.println("Fetching currency rates...");
            JsonObject fetchedCurrencyRates = fetcher.fetchCurrencyRates();
            if (fetchedCurrencyRates == null) {
                System.out.println("Error fetching currency rates. Check your API Key!");
                resultLabel.setText("Error fetching currency rates. Check your API Key!");
                return;
            }
            if (calculator == null) {
                calculator = new Calculator(
                        new CurrencyRates(fetchedCurrencyRates)
                );
            } else {
                calculator.setCurrencyRates(
                        new CurrencyRates(fetchedCurrencyRates)
                );
            }
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

    @FXML
    protected void onSettingsButtonClick() {
        CurrencyCalculatorApplication.stage.setScene(CurrencyCalculatorApplication.getSettingsScene());
    }
}
