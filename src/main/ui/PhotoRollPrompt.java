package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PhotoRoll;

public class PhotoRollPrompt {

    static PhotoRoll pr;

    public static PhotoRoll display() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create a New Photo Roll");

        Label promptLbl = new Label("Enter a name for your new roll:");
        TextField nameInput = new TextField();
        Button submitBtn = new Button("Enter");
        submitBtn.setOnAction(event -> {
            pr = new PhotoRoll(nameInput.getText());
            window.close();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(promptLbl, nameInput, submitBtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return pr;
    }
}
