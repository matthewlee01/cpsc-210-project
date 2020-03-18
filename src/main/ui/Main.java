package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import persistence.Reader;

import java.io.File;

public class Main extends Application {

    Stage window;
    Scene archiveScene;
    Scene photoListScene;
    Scene photoDisplayScene;

    ObservableList<PhotoRoll> photoRolls;
    ListView<PhotoRoll> photoRollsDisplay;
    PhotoRoll selectedRoll;

    ObservableList<PhotoEntry> photoEntries;
    PhotoEntry selectedPhoto;

    PhotoArchive archive;

    public static void main(String[] args) {
        // new PhotoArchiveApp();
        launch();
    }

    // MODIFIES: window, archive
    // EFFECTS: initializes window stage
    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Photo Archive");

        archive = Reader.readArchive(new File(PhotoArchiveApp.DATA_FILE));

        initArchiveScene();
        //initPhotoListScene();
        //initPhotoDisplayScene();

        window.setScene(archiveScene);
        window.show();

    }

    // MODIFIES: photoRolls, photoRollsDisplay
    // EFFECTS: initializes the archive menu scene
    private void initArchiveScene() {
        BorderPane layout = new BorderPane();
        VBox leftMenu = createArchiveLeftMenu();
        VBox rightMenu = createArchiveRightMenu();

        photoRolls = FXCollections.observableArrayList(archive.getPhotoRolls());
        photoRollsDisplay = new ListView<>(photoRolls);
        photoRollsDisplay.setOnMouseClicked(event -> {
            selectedRoll = photoRollsDisplay.getSelectionModel().getSelectedItem();
            if (selectedRoll == null) {
                event.consume();
            } else {
                rightMenu.setVisible(true);
                System.out.println(selectedRoll.getName());
            }
        });

        layout.setLeft(leftMenu);
        layout.setRight(rightMenu);
        layout.setCenter(photoRollsDisplay);
        archiveScene = new Scene(layout);
    }

    // EFFECTS: creates the left-hand menu panel for the archive scene
    private VBox createArchiveLeftMenu() {
        VBox leftMenu = new VBox();

        Button createRollBtn = new Button("Create New Photo Roll");
        createRollBtn.setOnAction(event -> createRollPrompt());
        leftMenu.getChildren().addAll(createRollBtn);

        return leftMenu;
    }

    // EFFECTS: creates the right-hand menu panel for the archive scene
    private VBox createArchiveRightMenu() {
        VBox rightMenu = new VBox();

        Button deleteSelectedRollBtn = new Button("Delete Selected Roll");
        deleteSelectedRollBtn.setOnAction(event -> {
            deleteSelectedRoll();
            rightMenu.setVisible(false);
        });

        rightMenu.getChildren().addAll(deleteSelectedRollBtn);
        rightMenu.setVisible(false);

        return rightMenu;
    }

    // MODIFIES: archive, photoRolls
    // EFFECTS: prompts the user for the creation of a new photo roll
    private void createRollPrompt() {
        PhotoRoll pr = PhotoRollPrompt.display();
        if (pr != null) {
            archive.addPhotoRoll(pr);
            photoRolls.setAll(archive.getPhotoRolls());
        }
    }

    // MODIFIES: archive, photoRolls, selectedRoll
    // EFFECTS: deletes the selected photo roll and sets selected to null
    private void deleteSelectedRoll() {
        archive.removePhotoRoll(selectedRoll);
        photoRolls.setAll(archive.getPhotoRolls());
        selectedRoll = null;
    }

}
