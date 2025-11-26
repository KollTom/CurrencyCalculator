package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

public class MainController {
    private Calculator calculator;
    private final Fetcher fetcher = new Fetcher(CurrencyCalculatorApplication.configController);

    private static MainController instance;

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    private Label resultLabel;
    @FXML
    private ComboBox<String> currencyBox;
    @FXML
    private ComboBox<String> targetCurrencyBox;
    @FXML
    private TextField moneyField;

    @FXML
    public void initialize() {
        instance = this;
        moneyField.setTextFormatter(new TextFormatter<>(change ->
                change.getText().matches("[0-9.]*") ? change : null
        ));
        fetch();
    }

    @FXML
    protected void onCalculateButtonClick() {
        if (calculator == null || Duration.between(calculator.getCurrencyRates().getFetchTime(), Instant.now()).toMinutes() >= 10) {
            fetch();
        }

        String moneyText = moneyField.getText();
        if (moneyText.isBlank()) {
            resultLabel.setText("Please provide an amount");
            return;
        }
        double money = Double.parseDouble(moneyField.getText());
        String currency = currencyBox.getValue();
        String targetCurrency = targetCurrencyBox.getValue();
        double result = calculator.calculate(money, currency, targetCurrency);
        resultLabel.setText("{money} {currency} is {result} {targetCurrency}"
                .replace("{money}", String.valueOf(money))
                .replace("{currency}", currency)
                .replace("{result}", String.valueOf(result))
                .replace("{targetCurrency}", targetCurrency)
        );
    }

    @FXML
    protected void onSettingsButtonClick() {
        CurrencyCalculatorApplication.stage.setScene(CurrencyCalculatorApplication.getSettingsScene());
    }

    public void fetch() {
        resultLabel.setText("");
        currencyBox.getItems().clear();
        targetCurrencyBox.getItems().clear();
        System.out.println("Fetching currency rates...");
        JsonObject fetchedCurrencyRates = fetcher.fetchCurrencyRates();
        if (fetchedCurrencyRates == null) {
            System.out.println("Error fetching currency rates. Check your API Key!");
            resultLabel.setText("Error fetching currency rates. Check your API Key!");
            return;
        }
        calculator = new Calculator(
                new CurrencyRates(fetchedCurrencyRates)
        );
        System.out.println("Successfully fetched currency rates!");

        Set<String> currencies = calculator.getCurrencyRates().getCurrencies();
        currencyBox.getItems().addAll(currencies);
        targetCurrencyBox.getItems().addAll(currencies);
        currencyBox.setValue("EUR");
        targetCurrencyBox.setValue("USD");
    }
}
