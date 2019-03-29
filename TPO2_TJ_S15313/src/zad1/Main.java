/**
 * @author Tomaszewska Justyna S15313
 */

package zad1;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();
        // ...
        // część uruchamiająca GUI
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Informacje");

        GridPane inputsGrid = new GridPane();
        inputsGrid.setPadding(new Insets(10, 10, 10, 10));
        inputsGrid.setVgap(8);
        inputsGrid.setHgap(10);

        Label cityLabel = new Label("Miasto:");
        GridPane.setConstraints(cityLabel, 0, 0);

        TextField cityInput = new TextField();
        cityInput.setPromptText("Warszawa");
        GridPane.setConstraints(cityInput, 2, 0);

        Label countryLabel = new Label("Kraj:");
        GridPane.setConstraints(countryLabel, 0, 1);

        TextField countryInput = new TextField();
        countryInput.setPromptText("Polska");
        GridPane.setConstraints(countryInput, 2, 1);

        Label weatherLabel = new Label("Informacja o aktualnej pogodzie:");
        GridPane.setConstraints(weatherLabel, 0, 4);

        TextArea weatherTextArea = new TextArea();
        weatherTextArea.setPromptText(new Service("Poland").getWeather("Warszawa"));
        weatherTextArea.setWrapText(true);
        GridPane.setConstraints(weatherTextArea, 2, 4);

        Label currencyOthersVal = new Label("Kurs waluty kraju do ");
        GridPane.setConstraints(currencyOthersVal, 0, 5);

        TextField currencyOthersNVal = new TextField();
        GridPane.setConstraints(currencyOthersNVal, 2, 5);

        ChoiceBox<String> availableCurrChoiceBox = new ChoiceBox<>();
        availableCurrChoiceBox.getItems().addAll(new Service("").getCurrenciesFromECB());
        availableCurrChoiceBox.setOnAction(event -> {
            currencyOthersNVal.setText(new Service(countryInput.getText())
                    .getRateFor(availableCurrChoiceBox.getValue()).toString());
        });
        GridPane.setConstraints(availableCurrChoiceBox, 1, 5);

        Label currencyPLNLabel = new Label("Kurs PLN do waluty wybranego kraju:");
        GridPane.setConstraints(currencyPLNLabel, 0, 6);

        TextField currencyPLNVal = new TextField();
        GridPane.setConstraints(currencyPLNVal, 2, 6);

        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        VBox root = new VBox();
        root.getChildren().add(webView);
        GridPane.setConstraints(root, 2, 7);
        Button checkButton = new Button("Sprawdź");
        GridPane.setConstraints(checkButton, 2, 2);
        checkButton.setOnAction(event -> {
            weatherTextArea.setText(new Service(countryInput.getText()).getWeather(cityInput.getText()));
            currencyPLNVal.setText(new Service(countryInput.getText()).getNBPRate().toString());
            webEngine.load("https://en.wikipedia.org/wiki/" + cityInput.getText());
        });

        inputsGrid.getChildren().addAll(cityLabel, cityInput, countryLabel, countryInput, checkButton,
                availableCurrChoiceBox,
                weatherLabel, weatherTextArea,
                currencyPLNLabel, currencyPLNVal,
                currencyOthersVal, currencyOthersNVal, root);

        Scene scene = new Scene(inputsGrid, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
