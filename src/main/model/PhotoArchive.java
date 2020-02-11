package model;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

// archive of all photo rolls
public class PhotoArchive {

    private ArrayList<PhotoRoll> photoRolls;

    // EFFECTS: creates a new photo archive
    public PhotoArchive() {
        photoRolls = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new photo roll to the archive
    public void addPhotoRoll(PhotoRoll photoRoll) {
        photoRolls.add(photoRoll);
    }

    // MODIFIES: this
    // EFFECTS: if pr is found, removes it and returns true,
    //          otherwise returns false
    public boolean removePhotoRoll(PhotoRoll photoRoll) {
        return photoRolls.remove(photoRoll);
    }

    // EFFECTS: returns a list of all film rolls
    public ArrayList<PhotoRoll> getPhotoRolls() {
        return photoRolls;
    }

    // EFFECTS: returns all photo entries from all rolls
    public ArrayList<PhotoEntry> getAllPhotoEntries() {
        ArrayList<PhotoEntry> photoList = new ArrayList<>();
        for (PhotoRoll pr : photoRolls) {
            photoList.addAll(pr.getPhotoEntries());
        }
        return photoList;
    }

    // EFFECTS: returns all photo entries that have the given tag
    public static ArrayList<PhotoEntry> filterPhotosByTag(ArrayList<PhotoEntry> photoList,
                                                          Tag tag) {
        ArrayList<PhotoEntry> filteredList = new ArrayList<>();
        for (PhotoEntry p : photoList) {
            if (p.hasTag(tag)) {
                filteredList.add(p);
            }
        }

        return filteredList;
    }
}


