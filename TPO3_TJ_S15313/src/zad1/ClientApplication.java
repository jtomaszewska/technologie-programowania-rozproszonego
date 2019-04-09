/**
 *
 *  @author Tomaszewska Justyna S15313
 *
 */

package zad1;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat");

        GridPane inputsGrid = new GridPane();
        inputsGrid.setPadding(new Insets(10, 10, 10, 10));
        inputsGrid.setVgap(8);
        inputsGrid.setHgap(10);

        TextArea readMessageTextArea = new TextArea();
        readMessageTextArea.setWrapText(true);
        GridPane.setConstraints(readMessageTextArea, 2, 4);

        TextArea writeMessageTextArea = new TextArea();
        writeMessageTextArea.setWrapText(true);
        GridPane.setConstraints(writeMessageTextArea, 2, 6);

        Button sentButton = new Button("Sent");
        GridPane.setConstraints(sentButton, 2, 7);
        sentButton.setOnAction(event -> {

        });

        inputsGrid.getChildren().addAll(readMessageTextArea, writeMessageTextArea, sentButton);

        Scene scene = new Scene(inputsGrid, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
