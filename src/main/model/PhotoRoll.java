package model;

import java.util.ArrayList;

// an organizational structure meant to represent an actual roll of film.
// all photos in a roll can have common tags that will automatically be
// added to their entries once they are assigned to a roll.
public class PhotoRoll {

    private ArrayList<PhotoEntry> photoEntries;
    private ArrayList<Tag> tags;
    private String name;

    // EFFECTS: creates a new photo roll
    public PhotoRoll(String name) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds photo entry p to this photo roll
    public void addPhotoEntry(PhotoEntry p) {
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
        return new ArrayList<>(); // stub
    }

    // MODIFIES: this
    // EFFECTS: if t is already in tags, does nothing and returns false
    //          otherwise adds t to tags, applies it to all photos, and returns true
    public void addTag(Tag t) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: if t is found, removes it and returns true,
    //          otherwise returns false
    public boolean removeTag(Tag t) {
        return true; // stub
    }

    // EFFECTS: returns all tags
    public ArrayList<Tag> getTags() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns name
    public String getName() {
        return ""; // stub
    }
}
