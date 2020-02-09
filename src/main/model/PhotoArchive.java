package model;

import java.util.ArrayList;

// archive of all photos entries and rolls
public class PhotoArchive {

    private ArrayList<PhotoEntry> photoEntries;
    private ArrayList<FilmRoll> filmRolls;

    // EFFECTS: creates a new photo archive
    public PhotoArchive() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a photo entry to the archive
    public void addPhotoEntry(PhotoEntry p) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a photo entry to the archive, preassigned to the film roll fr
    public void addPhotoEntry(PhotoEntry p, FilmRoll fr) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: if p is found, removes it and returns true,
    //          otherwise returns false
    public boolean removePhotoEntry(PhotoEntry p) {
        return true; // stub
    }

    // EFFECTS: returns all photo entries
    public ArrayList<PhotoEntry> getPhotoEntries() {
        return new ArrayList<>(); //stub
    }

    // EFFECTS: returns all photo entries in plist that have tag t
    public ArrayList<PhotoEntry> filterByTag(Tag t, ArrayList<PhotoEntry> plist) {
        return new ArrayList<>(); // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a new film roll to the archive
    public void addFilmRoll() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: if fr is found, removes it and returns true,
    //          otherwise returns false
    public boolean removeFilmRoll() {
        return true; // stub
    }

    // EFFECTS: returns a list of all film rolls
    public ArrayList<FilmRoll> getFilmRolls() {
        return new ArrayList<>();
    }

}
