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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import model.Tag;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// the main class to run the application
public class Main extends Application {

    Stage window;
    Scene archiveScene;
    Scene photoListScene;
    Scene photoDisplayScene;

    ObservableList<PhotoRoll> photoRolls;
    PhotoRoll selectedRoll;

    ObservableList<PhotoEntry> photoEntries;
    PhotoEntry selectedPhoto;

    ObservableList<Tag> tags;
    Tag selectedTag;
    Label description;

    FileChooser fc;

    ImageView photoDisplay;

    PhotoArchive archive;

    public static void main(String[] args) {
        launch();
    }

    // MODIFIES: window, archive
    // EFFECTS: initializes window stage
    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Photo Archive");
        window.setOnCloseRequest(event -> createSavePrompt());

        archive = Reader.readArchive(new File(PhotoArchiveApp.DATA_FILE));

        fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );

        initArchiveScene();
        initPhotoListScene();
        initPhotoDisplayScene();

        window.setScene(archiveScene);
        window.show();

    }

    private void createSavePrompt() {
        if (BooleanPrompt.display("Save?",
                "Would you like to save before exiting?")) {
            try {
                Writer.writeArchive(archive, new File(PhotoArchiveApp.DATA_FILE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    // MODIFIES: archive, photoRolls
    // EFFECTS: prompts the user for the creation of a new photo roll
    private void createRollPrompt() {
        PhotoRoll pr = new PhotoRoll(TextPrompt.display("Create New Photo Roll",
                "Enter a name for your new photo roll:"));
        archive.addPhotoRoll(pr);
        photoRolls.setAll(archive.getPhotoRolls());
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

    // MODIFIES: archive, photoRolls, selectedRoll
    // EFFECTS: deletes the selected photo roll and sets selected to null
    private void deleteSelectedRoll() {
        archive.removePhotoRoll(selectedRoll);
        photoRolls.setAll(archive.getPhotoRolls());
        selectedRoll = null;
    }

    // MODIFIES: photoEntries, selectedPhoto, tags, description, photoListScene
    // EFFECTS: initializes the photo list scene
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
                tags.setAll(selectedPhoto.getTags());
                description.setText(selectedPhoto.getDescription());
                System.out.println(selectedPhoto);
            }
        });

        layout.setLeft(leftMenu);
        layout.setRight(rightMenu);
        layout.setCenter(photoEntriesDisplay);
        photoListScene = new Scene(layout);
    }

    // EFFECTS: creates the left-hand menu for the photo list scene
    private VBox createPhotoListLeftMenu() {
        VBox leftMenu = new VBox();

        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> window.setScene(archiveScene));
        Button createEntryBtn = new Button("Create New Photo Entry");
        createEntryBtn.setOnAction(event -> createEntryPrompt());

        leftMenu.getChildren().addAll(backBtn, createEntryBtn);
        return leftMenu;
    }

    // MODIFIES: selectedRoll, photoEntries
    // EFFECTS: prompts the user to create a new photo entry
    private void createEntryPrompt() {
        PhotoEntry pe = new PhotoEntry(fc.showOpenDialog(window));
        selectedRoll.addPhotoEntry(pe);
        photoEntries.setAll(selectedRoll.getPhotoEntries());
    }

    // MODIFIES: description, tags
    // EFFECTS: creates the right-hand menu for the photo list scene
    private VBox createPhotoListRightMenu() {
        VBox rightMenu = new VBox();
        rightMenu.setVisible(false);

        Button viewPhotoBtn = createViewPhotoBtn();
        Button deletePhotoBtn = createDeletePhotoBtn(rightMenu);

        description = new Label();
        Button editDescriptionBtn = createEditDescriptionBtn();

        tags = FXCollections.observableArrayList();
        Button addTagBtn = createAddTagBtn();
        Button deleteTagBtn = createDeleteTagBtn();
        Button filterByTagBtn = createFilterByTagBtn();
        ListView<Tag> tagsDisplay = createTagsDisplay(deleteTagBtn, filterByTagBtn);

        rightMenu.getChildren().addAll(
                viewPhotoBtn, description, editDescriptionBtn,
                tagsDisplay, addTagBtn, deleteTagBtn,
                filterByTagBtn, deletePhotoBtn);
        return rightMenu;
    }

    // EFFECTS: creates the view photo button
    private Button createViewPhotoBtn() {
        Button btn = new Button("View Photo");
        btn.setOnAction(event -> {
            try {
                viewPhoto();
            } catch (FileNotFoundException e) {
                event.consume();
            }
        });
        return btn;
    }

    // MODIFIES: menu
    // EFFECTS: creates the delete photo button
    private Button createDeletePhotoBtn(VBox menu) {
        Button btn = new Button("Delete Selected Entry");
        btn.setOnAction(event -> {
            deletePhoto();
            menu.setVisible(false);
        });
        return btn;
    }

    // MODIFIES: selectedPhoto, description
    // EFFECTS: creates the edit description button
    private Button createEditDescriptionBtn() {
        Button btn = new Button("Edit Description");
        btn.setOnAction(event -> {
            selectedPhoto.setDescription(TextPrompt.display("Update Description",
                    "Enter a new description for this photo:"));
            description.setText(selectedPhoto.getDescription());
        });
        return btn;
    }

    // MODIFIES: selectedPhoto, tags
    // EFFECTS: creates the add tag button
    private Button createAddTagBtn() {
        Button btn = new Button("Create New Tag");
        btn.setOnAction(event -> {
            Tag t = new Tag(TextPrompt.display("Create New Tag",
                    "Enter the tag you would like to add:"));
            selectedPhoto.addTag(t);
            tags.setAll(selectedPhoto.getTags());
        });
        return btn;
    }

    // MODIFIES: selectedPhoto, selectedTag, tags
    // EFFECTS: creates the delete tag button
    private Button createDeleteTagBtn() {
        Button btn = new Button("Delete Selected Tag");
        btn.setVisible(false);
        btn.setOnAction(event -> {
            selectedPhoto.removeTag(selectedTag);
            selectedTag = null;
            tags.setAll(selectedPhoto.getTags());
            btn.setVisible(false);
        });
        return btn;
    }

    // MODIFIES: photoEntries
    // EFFECTS: filters the currently displayed photolist by the selected tag
    private Button createFilterByTagBtn() {
        Button btn = new Button("Filter Photos By Selected Tag");
        btn.setVisible(false);
        btn.setOnAction(event -> photoEntries.setAll(
                PhotoArchive.filterPhotosByTag(photoEntries, selectedTag.getTag())));
        return btn;
    }

    // MODIFIES: selectedTag, deleteBtn
    // EFFECTS: creates the tag display node
    private ListView<Tag> createTagsDisplay(Button deleteBtn, Button filterBtn) {
        ListView<Tag> lv = new ListView<>(tags);
        lv.setOnMouseClicked(event -> {
            selectedTag = lv.getSelectionModel().getSelectedItem();
            if (selectedTag == null) {
                event.consume();
            } else {
                deleteBtn.setVisible(true);
                filterBtn.setVisible(true);
                System.out.println(selectedTag);
            }
        });
        return lv;
    }

    // MODIFIES: photoDisplay, window
    // EFFECTS: loads the selected image file and shows it on the display scene
    private void viewPhoto() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(selectedPhoto.getPhotoFile());
        Image img = new Image(fis);
        photoDisplay.setImage(img);
        window.setScene(photoDisplayScene);
    }

    // MODIFIES: selectedRoll, photoEntries, selectedPhoto
    // EFFECTS: deletes the selected photo
    private void deletePhoto() {
        selectedRoll.removePhotoEntry(selectedPhoto);
        photoEntries.setAll(selectedRoll.getPhotoEntries());
        selectedPhoto = null;
    }


    // MODIFIES: photoDisplay, photoDisplayScene
    // EFFECTS: initializes the photo display scene
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
