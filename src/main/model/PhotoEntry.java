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
        this.photoFile = photoFile;
        date = new Date();
        description = DEFAULT_DESC;
        tags = new ArrayList<>();
    }

    // REQUIRES: photoFile is a valid photo file
    // EFFECTS: creates a new photofile, with date specified
    public PhotoEntry(File photoFile, Date date) {
        this.photoFile = photoFile;
        this.date = date;
        description = DEFAULT_DESC;
        tags = new ArrayList<>();
    }

    // REQUIRES: date is in the past
    // MODIFIES: this
    // EFFECTS: sets date
    public void setDate(Date date) {
        this.date = date;
    }

    // EFFECTS: returns date
    public Date getDate() {
        return date;
    }

    // MODIFIES: this
    // EFFECTS: if t is already in tags, does nothing and returns false
    //          otherwise adds t to tags and returns true
    public boolean addTag(Tag tag) {
        if (hasTag(tag)) {
            return false;
        } else {
            tags.add(tag);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: if t is in tags, removes it and returns true
    //          otherwise returns false
    public boolean removeTag(Tag tag) {
        return tags.remove(tag);
    }

    // EFFECTS: returns all tags
    public ArrayList<Tag> getTags() {
        return tags;
    }

    // EFFECTS: returns true if photo has tag t
    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    // MODIFIES: this
    // EFFECTS: sets the description
    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: returns the description
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the filename of the photo file
    public String getFilename() {
        return photoFile.getName();
    }
}
