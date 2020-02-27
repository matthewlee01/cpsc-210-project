package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PhotoEntryTest {

    File testFile;
    PhotoEntry pe;
    Date d1;
    Tag t1, t2;

    @BeforeEach
    void runBefore() {
        testFile = new File("data/testdata/tobs.jpg");
        pe = new PhotoEntry(testFile);
        d1 = new Date(1000);
        t1 = new Tag("#iso400");
        t2 = new Tag("#kodakfilm");
    }

    @Test
    void testConstructor() {
        assertEquals(PhotoEntry.DEFAULT_DESC, pe.getDescription());
        assertEquals(0, pe.getTags().size());
        assertEquals("tobs.jpg", pe.getFilename());
        assertEquals(testFile, pe.getPhotoFile());

        // overloaded constructor
        PhotoEntry po = new PhotoEntry(new File("data/testdata/tobs.jpg"), d1);
        assertEquals(d1, po.getDate());
        assertEquals(PhotoEntry.DEFAULT_DESC, po.getDescription());
        assertEquals(0, po.getTags().size());

    }

    @Test
    void testSetDate() {
        assertNotEquals(d1, pe.getDate());

        pe.setDate(d1);
        assertEquals(d1, pe.getDate());

    }

    @Test
    void testAddTag() {
        assertTrue(pe.addTag(t1));
        assertFalse(pe.addTag(t1));
        assertEquals(1, pe.getTags().size());

        assertTrue(pe.addTag(t2));
        assertEquals(2, pe.getTags().size());

        assertTrue(pe.hasTag(t1));
        assertTrue(pe.hasTag(t2));

        assertTrue(pe.hasTag(t1.getTag()));
        assertTrue(pe.hasTag(t2.getTag()));

    }

    @Test
    void testRemoveTag() {
        pe.addTag(t1);

        assertFalse(pe.removeTag(t2));
        assertTrue(pe.removeTag(t1));
        assertEquals(0, pe.getTags().size());
        assertFalse(pe.hasTag(t1));

    }

    @Test
    void testSetDescription() {
        String desc1 = "a charming picture of tobs";
        pe.setDescription(desc1);
        assertEquals(desc1, pe.getDescription());
    }

    @Test
    void testToString() {
        assertEquals(pe.getFilename(), pe.toString());
    }
}
