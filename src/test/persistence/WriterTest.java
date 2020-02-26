package persistence;

import model.PhotoArchive;
import model.PhotoEntry;
import model.PhotoRoll;
import model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {

    PhotoArchive pa;
    PhotoRoll pr;
    PhotoEntry p1, p2;

    private final static String testFile = "data/archive_write_test.json";

    @BeforeEach
    void runBefore() {
        pa = new PhotoArchive();
        pr = new PhotoRoll("dog pictures");
        p1 = new PhotoEntry(new File("data/tobs.jpg"));
        p2 = new PhotoEntry(new File("data/charlie.jpg"));

        pa.addPhotoRoll(pr);
        pr.addPhotoEntry(p1);
        pr.addPhotoEntry(p2);
        pr.addTag(new Tag("#dog"));
        p1.addTag(new Tag("#puppy"));
        p2.setDate(new Date(1000000000000L));
    }

    @Test
    void testWriteArchive() {

        Writer.writeArchive(pa, new File(testFile));

        try {
            PhotoArchive readArchive = Reader.readArchive(new File(testFile));
            PhotoRoll rr = readArchive.getPhotoRolls().get(0);
            PhotoEntry rp1 = rr.getPhotoEntries().get(0);
            PhotoEntry rp2 = rr.getPhotoEntries().get(1);
            assertEquals(1, readArchive.getPhotoRolls().size());
            assertEquals(2, readArchive.getAllPhotoEntries().size());
            assertEquals("dog pictures", rr.getName());
            assertTrue(rp1.hasTag("#dog"));
            assertTrue(rp2.hasTag("#dog"));
            assertTrue(rp1.hasTag("#puppy"));
            assertFalse(rp2.hasTag("#puppy"));
            assertEquals(PhotoEntry.DEFAULT_DESC, rp1.getDescription());
            assertEquals(1000000000000L, rp2.getDate().getTime());
        }
        catch(IOException e) {
            fail("file read should not fail here");
        }

    }

}
