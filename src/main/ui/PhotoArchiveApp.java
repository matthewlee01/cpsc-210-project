package ui;

import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhotoArchiveApp {

    private PhotoArchive archive;
    private Scanner input;
    private static final String INVALID_INPUT_MSG = "Invalid input! Please enter a valid selection.";
    private static final String BACK_CMD = "b";
    private static final String EXIT_CMD = "x";
    private static final String INT_REGEX = "\\d+";

    // EFFECTS: runs the photo archive application
    public PhotoArchiveApp() {
        launchPhotoArchive();
    }

    // EFFECTS: initiates the main archive menu
    public void launchPhotoArchive() {
        String command;
        boolean continueRunning = true;

        input = new Scanner(System.in);
        archive = new PhotoArchive();

        while (continueRunning) {
            displayArchiveMenu();
            command = input.next();
            command = command.toLowerCase();
            input.nextLine();

            if (command.equals(EXIT_CMD)) {
                continueRunning = false;
            } else {
                processArchiveCommand(command);
            }

        }
        System.out.println("Bye bye!");
    }

    // EFFECTS: displays the options for main archive menu
    public void displayArchiveMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("\tr - View all photo rolls");
        System.out.println("\tc - Create a new photo roll");
        System.out.println("\tp - View all photos");
        System.out.println("\tx - Exit");
    }

    // EFFECTS: initiates the action corresponding to the given command
    public void processArchiveCommand(String command) {
        if (command.equals("r")) {
            selectPhotoRoll();
        } else if (command.equals("c")) {
            createPhotoRoll();
        } else if (command.equals("p")) {
            displayAllPhotos();
        } else {
            System.out.println(INVALID_INPUT_MSG);
        }
    }

    // EFFECTS: displays menu to select a photo roll from the list of photo rolls
    public void selectPhotoRoll() {
        ArrayList<PhotoRoll> rl = archive.getPhotoRolls();
        boolean continueRunningSelectPhotoRoll = true;
        String command;

        while (continueRunningSelectPhotoRoll) {

            System.out.println("\nSelect a photo roll:");
            displayItemList(rl);
            System.out.println("\tb - Go back");

            command = input.next();
            command = command.toLowerCase();
            input.nextLine();

            if (command.equals(BACK_CMD)) {
                continueRunningSelectPhotoRoll = false;
            } else {
                processRollSelection(command, rl);
            }
        }
    }

    // EFFECTS: processes input from roll selection menu
    public void processRollSelection(String command,
                                     ArrayList<PhotoRoll> photoRolls) {
        if (command.matches(INT_REGEX)) {
            int i = Integer.parseInt(command);
            if (i > 0 && i <= photoRolls.size()) {
                launchPhotoRollMenu(photoRolls.get(i - 1));
            } else {
                System.out.println(INVALID_INPUT_MSG);
            }
        } else {
            System.out.println(INVALID_INPUT_MSG);
        }
    }

    // EFFECTS: displays an indexed list of all items
    public void displayItemList(List items) {

        if (items.isEmpty()) {
            System.out.println("No items found!");
        } else {
            int k = 1;

            while (k <= items.size()) {
                System.out.println("\t" + k + " - " + items.get(k - 1));
                k++;
            }
        }
    }

    // EFFECTS: launches photo roll menu display
    public void launchPhotoRollMenu(PhotoRoll photoRoll) {
        boolean continueRunningPhotoRollMenu = true;
        String command;

        while (continueRunningPhotoRollMenu) {
            displayPhotoRollMenu(photoRoll);

            command = input.next();
            command = command.toLowerCase();
            input.nextLine();

            if (command.equals(BACK_CMD)) {
                continueRunningPhotoRollMenu = false;
            } else if (command.equals("d")) {
                continueRunningPhotoRollMenu = false;
                archive.removePhotoRoll(photoRoll);
                System.out.println("Photo roll " + photoRoll.getName() + " deleted!");
            } else {
                processPhotoRollCommand(command, photoRoll);
            }
        }
    }

    // EFFECTS: processes command from photo roll menu
    public void processPhotoRollCommand(String command,
                                        PhotoRoll photoRoll) {
        if (command.equals("p")) {
            selectPhotoEntry(photoRoll.getPhotoEntries());
        } else if (command.equals("a")) {
            addTag(photoRoll);
        } else if (command.equals("r")) {
            removeTag(photoRoll);
        } else if (command.equals("c")) {
            createPhotoEntry(photoRoll);
        } else {
            System.out.println(INVALID_INPUT_MSG);
        }

    }

    // MODIFIES: taggable
    // EFFECTS: adds the input tag to the item
    public void addTag(Taggable taggable) {
        String tag;
        System.out.println("Enter the tag you would like to add:");

        tag = input.next();
        tag = tag.toLowerCase();
        input.nextLine();

        taggable.addTag(new Tag(tag));
        System.out.println("Tag " + tag + " successfully added!");

    }

    // MODIFIES: taggable
    // EFFECTS: attempts to remove the input tag from the given item
    public void removeTag(Taggable taggable) {
        String tag;
        System.out.println("Enter the tag you would like to remove:");

        tag = input.next();
        tag = tag.toLowerCase();
        input.nextLine();

        for (Tag t : taggable.getTags()) {
            if (t.getTag().equals(tag)) {
                taggable.removeTag(t);
                System.out.println("Tag " + tag + " successfully removed!");
                return;
            }
        }
        System.out.println("Tag not found!");

    }

    // MODIFIES: photoRoll
    // EFFECTS: creates a new photo entry in photoRoll
    public void createPhotoEntry(PhotoRoll photoRoll) {
        String filename;
        System.out.println("Enter the filename of the image:");

        filename = input.next();
        input.nextLine();

        File img = new File(filename);
        photoRoll.addPhotoEntry(new PhotoEntry(img));
        System.out.println("Photo " + img.getName() + " successfully added to roll " + photoRoll.getName());
    }

    // EFFECTS: displays photo roll menu items
    public void displayPhotoRollMenu(PhotoRoll photoRoll) {
        System.out.println("\nSelected roll: " + photoRoll.getName());
        System.out.println("Tags: " + photoRoll.getTags());

        System.out.println("Select an option:");
        System.out.println("\tp - View photos in this roll");
        System.out.println("\ta - Add a tag");
        System.out.println("\tr - Remove a tag");
        System.out.println("\tc - Create a new photo entry");
        System.out.println("\td - Delete this photo roll");
        System.out.println("\tb - Go back");
    }

    // MODIFIES: archive
    // EFFECTS: creates a new roll with input name
    public void createPhotoRoll() {
        System.out.println("Enter the name of your new roll:");
        String name = input.nextLine();

        archive.addPhotoRoll(new PhotoRoll(name));
        System.out.println("Roll " + name + " created!");
    }

    // EFFECTS: displays all photos from archive
    public void displayAllPhotos() {
        selectPhotoEntry(archive.getAllPhotoEntries());
    }

    // EFFECTS: displays photo entry selection menu
    public void selectPhotoEntry(ArrayList<PhotoEntry> photoEntries) {
        boolean continueRunningSelectPhotoEntry = true;
        String command;

        while (continueRunningSelectPhotoEntry) {

            System.out.println("\nSelect a photo entry:");
            displayItemList(photoEntries);
            System.out.println("\tf - Filter entries by tag");
            System.out.println("\tb - Go back");

            command = input.next();
            input.nextLine();
            command = command.toLowerCase();

            if (command.equals(BACK_CMD)) {
                continueRunningSelectPhotoEntry = false;
            } else if (command.equals("f")) {
                continueRunningSelectPhotoEntry = false;
                filterPhotoEntriesByTag(photoEntries);
            } else {
                processEntrySelection(command, photoEntries);
            }

        }
    }

    // EFFECTS: processes input from photo entry selection menu
    public void processEntrySelection(String command,
                                      ArrayList<PhotoEntry> photoEntries) {
        if (command.matches(INT_REGEX)) {
            int i = Integer.parseInt(command);
            if (i > 0 && i <= photoEntries.size()) {
                launchPhotoEntryMenu(photoEntries.get(i - 1));
            } else {
                System.out.println(INVALID_INPUT_MSG);
            }
        } else {
            System.out.println(INVALID_INPUT_MSG);
        }
    }

    // EFFECTS: filters list by input tag then relaunches photo selection
    public void filterPhotoEntriesByTag(ArrayList<PhotoEntry> photoEntries) {
        System.out.println("Enter the tag you would like to filter by:");
        String tag = input.next();
        tag = tag.toLowerCase();
        input.nextLine();

        selectPhotoEntry(PhotoArchive.filterPhotosByTag(photoEntries, tag));
    }

    // EFFECTS: launches photo entry menu
    public void launchPhotoEntryMenu(PhotoEntry photoEntry) {
        boolean continueRunningPhotoEntryMenu = true;
        String command;

        while (continueRunningPhotoEntryMenu) {

            displayPhotoEntryMenu(photoEntry);
            command = input.next();
            command = command.toLowerCase();
            input.nextLine();

            if (command.equals(BACK_CMD)) {
                continueRunningPhotoEntryMenu = false;
            } else if (command.equals("d")) {
                continueRunningPhotoEntryMenu = false;
                deletePhotoEntry(photoEntry);
            } else {
                processPhotoEntryCommand(command, photoEntry);
            }

        }
    }

    // EFFECTS: processes command from photo entry menu
    public void processPhotoEntryCommand(String command, PhotoEntry photoEntry) {
        if (command.equals("e")) {
            editDescription(photoEntry);
        } else if (command.equals("a")) {
            addTag(photoEntry);
        } else if (command.equals("r")) {
            removeTag(photoEntry);
        } else {
            System.out.println(INVALID_INPUT_MSG);
        }
    }

    // MODIFIES: photoEntry
    // EFFECTS: sets description to new input value
    public void editDescription(PhotoEntry photoEntry) {
        System.out.println("Enter a your new description:");
        String desc = input.nextLine();
        photoEntry.setDescription(desc);
        System.out.println("Description updated!");
    }

    // MODIFIES: archive
    // deletes a photo entry from the archive
    public void deletePhotoEntry(PhotoEntry photoEntry) {
        for (PhotoRoll pr : archive.getPhotoRolls()) {
            if (pr.getPhotoEntries().contains(photoEntry)) {
                pr.removePhotoEntry(photoEntry);
                System.out.println("Entry " + photoEntry.getFilename() + " removed!");
                return;
            }
        }

        System.out.println("Entry not found!");

    }

    // EFFECTS: displays the photo entry menu options
    public void displayPhotoEntryMenu(PhotoEntry photoEntry) {
        System.out.println("\nSelected photo entry: " + photoEntry.getFilename());
        System.out.println("Description: " + photoEntry.getDescription());
        System.out.println("Date created: " + photoEntry.getDate());
        System.out.println("Tags: " + photoEntry.getTags());
        System.out.println("\te - Edit description");
        System.out.println("\ta - Add a tag");
        System.out.println("\tr - Remove a tag");
        System.out.println("\td - Delete this photo entry");
        System.out.println("\tb - Go back");
    }


}
