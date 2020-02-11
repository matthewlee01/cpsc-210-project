package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PhotoEntryTest {

    PhotoEntry pe;
    Tag t1, t2;
    Date d1;

    @BeforeEach
    void runBefore() {
        d1 = new Date(1000);

        pe = new PhotoEntry(new File("data/tobs.jpg"));

        t1 = new Tag("#iso400");
        t2 = new Tag("#kodakfilm");
    }

    @Test
    void testConstructor() {
        assertEquals(PhotoEntry.DEFAULT_DESC, pe.getDescription());
        assertEquals(0, pe.getTags().size());

        // overloaded constructor
        PhotoEntry po = new PhotoEntry(new File("data/tobs.jpg"), d1);
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


}
