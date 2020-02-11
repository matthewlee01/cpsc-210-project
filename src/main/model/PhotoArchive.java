package model;

import java.util.ArrayList;

// archive of all photo rolls
public class PhotoArchive {

    private ArrayList<PhotoRoll> photoRolls;

    // EFFECTS: creates a new photo archive
    public PhotoArchive() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a new photo roll to the archive
    public void addPhotoRoll(PhotoRoll pr) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: if pr is found, removes it and returns true,
    //          otherwise returns false
    public boolean removePhotoRoll(PhotoRoll pr) {
        return true; // stub
    }

    // EFFECTS: returns a list of all film rolls
    public ArrayList<PhotoRoll> getPhotoRolls() {
        return new ArrayList<>();
    }

    // EFFECTS: returns all photo entries from all rolls
    public ArrayList<PhotoEntry> getAllPhotoEntries() {
        return new ArrayList<>(); //stub
    }

    // EFFECTS: returns all photo entries in pl that have tag t
    public static ArrayList<PhotoEntry> filterPhotosByTag(ArrayList<PhotoEntry> pl,
                                                          Tag t) {
        return new ArrayList<>(); // stub
    }
}


