package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// a binary input popup window
public class BooleanPrompt {

    static Boolean b;

    // MODIFIES: b
    // EFFECTS: prompts the user for a yes/no input
    public static Boolean display(String title, String prompt) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label promptLbl = new Label(prompt);
        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(event -> {
            b = true;
            window.close();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(event -> {
            b = false;
            window.close();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(promptLbl, yesBtn, noBtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return b;

    }
}
