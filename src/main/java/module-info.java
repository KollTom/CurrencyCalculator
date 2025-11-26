module io.github.kolltom.currencycalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires io.github.cdimascio.dotenv.java;
    requires java.net.http;


    opens io.github.kolltom.currencycalculator to javafx.fxml;
    exports io.github.kolltom.currencycalculator;
}