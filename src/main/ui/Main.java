package ui;

// the main class to run the application
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--nogui")) {
            new PhotoArchiveUI();
        } else {
            PhotoArchiveGUI.initialize();
        }
    }

}
