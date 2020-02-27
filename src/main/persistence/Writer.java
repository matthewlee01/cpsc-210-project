package persistence;

import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    // MODIFIES: file
    // EFFECTS: encodes the given archive into a JSON object and writes it to file
    public static void writeArchive(PhotoArchive photoArchive, File file) throws IOException {
        FileWriter fw;
        fw = new FileWriter(file);
        JSONArray ja = encodePhotoArchive(photoArchive);
        fw.write(ja.toString());
        fw.close();
    }

    // EFFECTS: encodes a photo archive as a list of photo rolls
    private static JSONArray encodePhotoArchive(PhotoArchive pa) {
        JSONArray ja = new JSONArray();
        for (PhotoRoll pr : pa.getPhotoRolls()) {
            ja.put(encodePhotoRoll(pr));
        }
        return ja;
    }

    // EFFECTS: encodes a photo roll as a JSON object
    private static JSONObject encodePhotoRoll(PhotoRoll pr) {
        JSONObject jpr = new JSONObject();
        jpr.put("name", pr.getName());

        JSONArray tags = new JSONArray();
        for (Tag t : pr.getTags()) {
            tags.put(t.getTag());
        }
        jpr.put("tags", tags);

        JSONArray photoEntries = new JSONArray();
        for (PhotoEntry pe : pr.getPhotoEntries()) {
            photoEntries.put(encodePhotoEntry(pe));
        }
        jpr.put("photo_entries", photoEntries);

        return jpr;
    }

    // EFFECTS: encodes a photo entry as a JSON object
    private static JSONObject encodePhotoEntry(PhotoEntry pe) {
        JSONObject jpe = new JSONObject();
        jpe.put("file", pe.getPhotoFile().getPath());
        jpe.put("description", pe.getDescription());
        jpe.put("date_long", pe.getDate().getTime());

        JSONArray tags = new JSONArray();
        for (Tag t : pe.getTags()) {
            tags.put(t.getTag());
        }
        jpe.put("tags", tags);

        return jpe;
    }
}
