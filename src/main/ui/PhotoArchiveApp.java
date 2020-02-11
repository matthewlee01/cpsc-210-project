package ui;

import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PhotoArchiveApp {

    private PhotoArchive archive;
    private Scanner input;
    private boolean continueRunning;
    private static final String INVALID_INPUT_MSG = "Invalid input! Please enter a valid selection.";

    // EFFECTS: runs the photo archive application
    public PhotoArchiveApp() {
        launchPhotoArchive();
    }

    public void launchPhotoArchive() {
        int command;
        continueRunning = true;

        input = new Scanner(System.in);
        archive = new PhotoArchive();

        while (continueRunning) {
            displayArchiveMenu();
            command = input.nextInt();

            if (command == 4) {
                continueRunning = false;
            } else {
                processArchiveCommand(command);
            }

        }
        System.out.println("Bye bye!");
    }

    public void displayArchiveMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("\t0 - View all photo rolls");
        System.out.println("\t1 - Create a new photo roll");
        System.out.println("\t2 - Remove a photo roll");
        System.out.println("\t3 - View all photos");
        System.out.println("\t4 - Exit");
    }

    public void processArchiveCommand(int command) {
        if (command == 0) {
            selectPhotoRoll();
        } else if (command == 1) {
            createPhotoRoll();
        } else if (command == 2) {
            removePhotoRoll();
        } else if (command == 3) {
            displayAllPhotos();
        } else {
            System.out.println(INVALID_INPUT_MSG);
        }
    }

    public void selectPhotoRoll() {
        ArrayList<PhotoRoll> pr = archive.getPhotoRolls();
        boolean continueRunningSelectPhotoRoll = true;
        int command;

        while (continueRunningSelectPhotoRoll) {
            displayPhotoRollList(pr);

            command = input.nextInt();

            if (command == pr.size()) {
                continueRunningSelectPhotoRoll = false;
            } else if (command == (pr.size() + 1)) {
                continueRunningSelectPhotoRoll = false;
                continueRunning = false;
            } else if (0 <= command && command < pr.size()) {
                launchPhotoRollMenu(pr.get(command));
            } else {
                System.out.println(INVALID_INPUT_MSG);
            }
        }
    }

    public void displayPhotoRollList(ArrayList<PhotoRoll> photoRolls) {
        int k = 0;

        while (k < photoRolls.size()) {
            System.out.println("\t" + k + " - " + photoRolls.get(k).getName());
            k++;
        }
        System.out.println("\t" + k + " - Go back");
        System.out.println("\t" + (k + 1) + " - Exit");
    }

    public void launchPhotoRollMenu(PhotoRoll photoRoll) {
        // stub
    }

    public void createPhotoRoll() {
        // stub
    }

    public void removePhotoRoll() {
        // stub
    }

    public void displayAllPhotos() {
        // stub
    }
}
