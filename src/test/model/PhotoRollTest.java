package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PhotoRollTest {

    PhotoRoll pr;
    PhotoEntry p1, p2;
    Tag t1, t2;

    @BeforeEach
    void runBefore() {
        pr = new PhotoRoll("roll1");

        p1 = new PhotoEntry(new File("data/tobs.jpg"));
        p2 = new PhotoEntry(new File("data/tobs.jpg"));

        t1 = new Tag("#good");
        t2 = new Tag("#cool");
    }

    @Test
    void testConstructor() {
        assertEquals("roll1", pr.getName());
        assertEquals(0, pr.getTags().size());
        assertEquals(0, pr.getPhotoEntries().size());
    }

    @Test
    void testAddPhotoEntry() {
        pr.addPhotoEntry(p1);
        assertEquals(1, pr.getPhotoEntries().size());

        pr.addPhotoEntry(p2);
        assertEquals(2, pr.getPhotoEntries().size());

        assertTrue(pr.getPhotoEntries().contains(p1));
        assertTrue(pr.getPhotoEntries().contains(p2));
    }

    @Test
    void testRemovePhotoEntry() {
        pr.addPhotoEntry(p1);

        assertFalse(pr.removePhotoEntry(p2));
        assertTrue(pr.getPhotoEntries().contains(p1));

        assertTrue(pr.removePhotoEntry(p1));
        assertEquals(0, pr.getPhotoEntries().size());
    }

    @Test
    void testAddTag() {
        pr.addPhotoEntry(p1);

        assertTrue(pr.addTag(t1));
        assertEquals(1, pr.getTags().size());
        assertTrue(pr.getTags().contains(t1));
        assertTrue(p1.hasTag(t1));

        pr.addPhotoEntry(p2);
        assertTrue(p2.hasTag(t1));

        assertFalse(pr.addTag(t1));
        assertTrue(pr.addTag(t2));
        assertEquals(2, pr.getTags().size());
        assertTrue(p1.hasTag(t2));
        assertTrue(p2.hasTag(t2));

        assertTrue(pr.getTags().contains(t1));
        assertTrue(pr.getTags().contains(t2));
    }

    @Test
    void testRemoveTag() {
        pr.addPhotoEntry(p1);
        pr.addTag(t1);

        assertFalse(pr.removeTag(t2));
        assertTrue(p1.hasTag(t1));
        assertTrue(pr.getTags().contains(t1));

        assertTrue(pr.removeTag(t1));
        assertFalse(p1.hasTag(t1));
        assertFalse(pr.getTags().contains(t1));

    }

    @Test
    void testToString() {
        assertEquals(pr.getName(), pr.toString());
    }
}
