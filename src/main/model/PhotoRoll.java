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
        this.name = name;
        photoEntries = new ArrayList<>();
        tags = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds photo entry p to this photo roll and applies all tags to it
    public void addPhotoEntry(PhotoEntry photo) {
        photoEntries.add(photo);
        for (Tag t : tags) {
            photo.addTag(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: if p is found, removes it and returns true,
    //          otherwise returns false
    public boolean removePhotoEntry(PhotoEntry photo) {
        return photoEntries.remove(photo);
    }

    // EFFECTS: returns all photo entries
    public ArrayList<PhotoEntry> getPhotoEntries() {
        return photoEntries;
    }

    // MODIFIES: this
    // EFFECTS: if t is already in tags, does nothing and returns false
    //          otherwise adds t to tags, applies it to all photos, and returns true
    public boolean addTag(Tag tag) {
        if (tags.contains(tag)) {
            return false;
        } else {
            tags.add(tag);
            for (PhotoEntry p : photoEntries) {
                p.addTag(tag);
            }
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: if t is found, removes it and returns true,
    //          otherwise returns false
    public boolean removeTag(Tag tag) {
        for (PhotoEntry p : photoEntries) {
            p.removeTag(tag);
        }
        return tags.remove(tag);
    }

    // EFFECTS: returns all tags
    public ArrayList<Tag> getTags() {
        return tags;
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }
}
