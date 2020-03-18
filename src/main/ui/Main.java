package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import model.Tag;
import persistence.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

    Stage window;
    Scene archiveScene;
    Scene photoListScene;
    Scene photoDisplayScene;

    ObservableList<PhotoRoll> photoRolls;
    PhotoRoll selectedRoll;

    ObservableList<PhotoEntry> photoEntries;
    PhotoEntry selectedPhoto;

    ImageView photoDisplay;

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
        initPhotoListScene();
        initPhotoDisplayScene();

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

        ListView<PhotoRoll> photoRollsDisplay = new ListView<>(photoRolls);
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

        Button viewRollPhotosBtn = new Button("View Photos");
        viewRollPhotosBtn.setOnAction(event -> {
            photoEntries.setAll(selectedRoll.getPhotoEntries());
            System.out.println(photoEntries);
            window.setScene(photoListScene);
        });

        rightMenu.getChildren().addAll(deleteSelectedRollBtn, viewRollPhotosBtn);
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

    private void initPhotoListScene() {
        BorderPane layout = new BorderPane();
        VBox leftMenu = createPhotoListLeftMenu();
        VBox rightMenu = createPhotoListRightMenu();

        photoEntries = FXCollections.observableArrayList();
        ListView<PhotoEntry> photoEntriesDisplay = new ListView<>(photoEntries);
        photoEntriesDisplay.setOnMouseClicked(event -> {
            selectedPhoto = photoEntriesDisplay.getSelectionModel().getSelectedItem();
            if (selectedPhoto == null) {
                event.consume();
            } else {
                rightMenu.setVisible(true);
                System.out.println(selectedPhoto);
            }
        });

        layout.setLeft(leftMenu);
        layout.setRight(rightMenu);
        layout.setCenter(photoEntriesDisplay);
        photoListScene = new Scene(layout);
    }

    private VBox createPhotoListLeftMenu() {
        VBox leftMenu = new VBox();

        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> window.setScene(archiveScene));
        leftMenu.getChildren().addAll(backBtn);

        return leftMenu;
    }

    private VBox createPhotoListRightMenu() {
        VBox rightMenu = new VBox();
        rightMenu.setVisible(false);

        Button viewPhotoBtn = new Button("View Photo");
        viewPhotoBtn.setOnAction(event -> {
            try {
                FileInputStream fis = new FileInputStream(selectedPhoto.getPhotoFile());
                Image img = new Image(fis);
                photoDisplay.setImage(img);
                window.setScene(photoDisplayScene);
            } catch (FileNotFoundException e) {
                event.consume();
            }
        });

        rightMenu.getChildren().addAll(viewPhotoBtn);
        return rightMenu;

    }

    private void initPhotoDisplayScene() {
        BorderPane layout = new BorderPane();

        photoDisplay = new ImageView();

        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> window.setScene(photoListScene));
        VBox leftMenu = new VBox();
        leftMenu.getChildren().addAll(backBtn);

        layout.setCenter(photoDisplay);
        layout.setLeft(leftMenu);

        photoDisplayScene = new Scene(layout);
    }
}
