package persistence;

import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class Reader {

    // EFFECTS: returns a PhotoArchive loaded from the save file
    public static PhotoArchive readArchive(File file) throws IOException {
        PhotoArchive pa = new PhotoArchive();
        ArrayList<PhotoRoll> photoRolls = parsePhotoRolls(readFile(file));
        for (PhotoRoll pr : photoRolls) {
            pa.addPhotoRoll(pr);
        }
        return pa;
    }

    // EFFECTS: attempts to read the given file into a JSON array
    private static JSONArray readFile(File file) throws IOException {
        String content = FileUtils.readFileToString(file, "utf-8");
        return new JSONArray(content);
    }

    // EFFECTS: parses a JSONArray of JSONObject photo rolls into an ArrayList of PhotoRoll objects
    private static ArrayList<PhotoRoll> parsePhotoRolls(JSONArray ja) {
        ArrayList<PhotoRoll> photoRolls = new ArrayList<>();

        for (int i = 0; i < ja.length(); i++) {
            photoRolls.add(parsePhotoRoll(ja.getJSONObject(i)));
        }

        return photoRolls;
    }

    // EFFECTS: parses a JSONObject photo roll into a PhotoRoll object
    private static PhotoRoll parsePhotoRoll(JSONObject jsonRoll) {
        PhotoRoll pr = new PhotoRoll(jsonRoll.getString("name"));
        JSONArray tags = jsonRoll.getJSONArray("tags");
        JSONArray photoEntries = jsonRoll.getJSONArray("photo_entries");

        for (int i = 0; i < tags.length(); i++) {
            pr.addTag(new Tag(tags.getString(i)));
        }

        for (int i = 0; i < photoEntries.length(); i++) {
            pr.addPhotoEntry(parsePhotoEntry(photoEntries.getJSONObject(i)));
        }

        return pr;
    }

    // EFFECTS: parses a JSONObject photo entry into a PhotoEntry object
    private static PhotoEntry parsePhotoEntry(JSONObject jsonPE) {
        File file = new File(jsonPE.getString("file"));
        PhotoEntry pe = new PhotoEntry(file);
        JSONArray tags = jsonPE.getJSONArray("tags");

        pe.setDescription(jsonPE.getString("description"));
        pe.setDate(new Date(jsonPE.getLong("date_long")));

        for (int i = 0; i < tags.length(); i++) {
            pe.addTag(new Tag(tags.getString(i)));
        }

        return pe;
    }

}
