package model;

import java.io.File;
import java.util.Date;
import java.util.ArrayList;

// a photo with its associated data and tags
public class PhotoEntry {

    public static final String DEFAULT_DESC = "No description added yet.";

    private File photoFile;
    private Date date;
    private String description;
    private ArrayList<Tag> tags;

    // REQUIRES: photoFile is a valid photo file
    // EFFECTS: creates a new photofile, with date defaulted to right now
    public PhotoEntry(File photoFile) {
        // stub
    }

    // REQUIRES: photoFile is a valid photo file
    // EFFECTS: creates a new photofile, with date specified
    public PhotoEntry(File photoFile, Date date) {
        // stub
    }

    // REQUIRES: date is in the past
    // MODIFIES: this
    // EFFECTS: sets date
    public void setDate(Date date) {
        // stub
    }

    // EFFECTS: returns date
    public Date getDate() {
        return new Date(); // stub
    }

    // MODIFIES: this
    // EFFECTS: if t is already in tags, does nothing and returns false
    //          otherwise adds t to tags and returns true
    public boolean addTag(Tag t) {
        return true; // stub
    }

    // MODIFIES: this
    // EFFECTS: if t is in tags, removes it and returns true
    //          otherwise returns false
    public boolean removeTag(Tag t) {
        return true; // stub
    }

    // EFFECTS: returns all tags
    public ArrayList<Tag> getTags() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns true if photo has tag t
    public boolean hasTag(Tag t) {
        return true; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets the description
    public void setDescription(String s) {
        // stub
    }

    public String getDescription() {
        return ""; // stub
    }
}
