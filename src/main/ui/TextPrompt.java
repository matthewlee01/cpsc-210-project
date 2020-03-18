package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// a text input popup window
public class TextPrompt {

    static String s;

    // MODIFIES: s
    // EFFECTS: prompts the user for a text input
    public static String display(String title, String prompt) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label promptLbl = new Label(prompt);
        TextField nameInput = new TextField();
        Button submitBtn = new Button("Enter");
        submitBtn.setOnAction(event -> {
            s = nameInput.getText();
            window.close();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(promptLbl, nameInput, submitBtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return s;
    }
}
