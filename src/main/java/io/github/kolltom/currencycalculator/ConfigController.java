package io.github.kolltom.currencycalculator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigController {
    @FXML
    private TextField apiKeyField;

    private static final File configFile = new File("config.json");

    @FXML
    public void initialize() {
        if (!configFile.isFile()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            apiKeyField.setText(getApiKey());
        }
    }

    @FXML
    protected void onBackButtonClick() {
        saveApiKey(apiKeyField.getText());
        CurrencyCalculatorApplication.stage.setScene(CurrencyCalculatorApplication.getMainScene());
    }

    public String getApiKey() {
        try {
            String jsonValue = String.join("", Files.readAllLines(configFile.toPath()));
            JsonObject configJsonObject = JsonParser.parseString(jsonValue).getAsJsonObject();
            return configJsonObject.get("api_key").getAsString();
        } catch (IOException | IllegalStateException e) {
            return null;
        }
    }

    private void saveApiKey(String api_key) {
        try {
            String jsonValue = String.join("", Files.readAllLines(configFile.toPath()));
            JsonObject configJsonObject;
            try {
                configJsonObject = JsonParser.parseString(jsonValue).getAsJsonObject();
            } catch (IllegalStateException e) {
                configJsonObject = new JsonObject();
            }
            configJsonObject.addProperty("api_key", api_key);
            Files.writeString(configFile.toPath(), configJsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
