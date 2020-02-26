package persistence;

import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    PhotoArchive pa;

    @Test
    void testReadArchive() {
        try {
            pa = Reader.readArchive(new File("data/test_archive.json"));
            assertEquals(3, pa.getPhotoRolls().size());

            PhotoRoll r1 = pa.getPhotoRolls().get(0);
            assertEquals("roll1", r1.getName());
            assertEquals("#cute", r1.getTags().get(0).getTag());

            PhotoEntry p1 = r1.getPhotoEntries().get(0);
            assertEquals("tobs.jpg", p1.getFilename());
            assertEquals(1500000000000L, p1.getDate().getTime());
            assertEquals("the tobdog", p1.getDescription());
            assertEquals("#dog", p1.getTags().get(0).getTag());

            PhotoRoll r2 = pa.getPhotoRolls().get(1);
            assertEquals(0, r2.getPhotoEntries().size());
            assertEquals(0, r2.getTags().size());

            assertEquals(3, pa.getAllPhotoEntries().size());
        }
        catch (IOException e) {
            fail("file read should not fail here");
        }
        // stub

    }

    @Test
    void testFailReadArchive() {
        try {
            pa = Reader.readArchive(new File("nonexistent_file.json"));
            fail();
        }
        catch(IOException e) {
            // expected
        }
    }
}
