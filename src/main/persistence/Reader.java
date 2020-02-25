package persistence;

import model.PhotoArchive;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Reader {

    // EFFECTS: returns a PhotoArchive loaded from the save file
    public static PhotoArchive readArchive(File file) throws IOException {
        System.out.println(readFile(file));
        return new PhotoArchive();
        // stub
    }

    // EFFECTS: attempts to read the given file into a JSON array
    private static JSONArray readFile(File file) throws IOException {
        String content = FileUtils.readFileToString(file, "utf-8");
        return new JSONArray(content);
    }
}
