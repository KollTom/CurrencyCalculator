package io.github.kolltom.currencycalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrencyCalculatorApplication extends Application {
    public static final ConfigController configController = new ConfigController();
    public static Stage stage;

    private static Scene mainScene;
    private static Scene settingsScene;

    @Override
    public void start(Stage stage)  {
        CurrencyCalculatorApplication.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Currency Calculator (JavaFX)");

        if (configController.getApiKey() == null) {
            stage.setScene(getSettingsScene());
        } else {
            stage.setScene(getMainScene());
        }
        stage.show();
    }

    public static Scene getMainScene() {
        if (mainScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(CurrencyCalculatorApplication.class.getResource("main-view.fxml"));
            try {
                mainScene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return mainScene;
    }
    public static Scene getSettingsScene() {
        if (settingsScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(CurrencyCalculatorApplication.class.getResource("settings-view.fxml"));
            try {
                settingsScene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return settingsScene;
    }
}
