package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PhotoArchiveTest {

    PhotoArchive pa;
    PhotoRoll r1, r2;

    @BeforeEach
    void runBefore() {
        pa = new PhotoArchive();

        r1 = new PhotoRoll("roll1");
        r2 = new PhotoRoll("roll2");
    }

    @Test
    void testConstructor() {
        assertEquals(0, pa.getPhotoRolls().size());

    }

    @Test
    void testAddPhotoRoll() {
        pa.addPhotoRoll(r1);
        pa.addPhotoRoll(r2);

        assertTrue(pa.getPhotoRolls().contains(r1));
        assertTrue(pa.getPhotoRolls().contains(r2));
        assertEquals(2, pa.getPhotoRolls().size());

    }

    @Test
    void testRemovePhotoRoll() {
        pa.addPhotoRoll(r1);

        assertFalse(pa.removePhotoRoll(r2));
        assertTrue(pa.getPhotoRolls().contains(r1));

        assertTrue(pa.removePhotoRoll(r1));
        assertEquals(0, pa.getPhotoRolls().size());
    }

    @Test
    void testGetAllPhotoEntries() {

        PhotoEntry p1 = new PhotoEntry(new File("data/tobs.jpg"));
        PhotoEntry p2 = new PhotoEntry(new File("data/tobs.jpg"));
        PhotoEntry p3 = new PhotoEntry(new File("data/tobs.jpg"));

        r1.addPhotoEntry(p1);
        r2.addPhotoEntry(p2);

        pa.addPhotoRoll(r1);
        pa.addPhotoRoll(r2);

        assertEquals(2, pa.getAllPhotoEntries().size());
        assertTrue(pa.getAllPhotoEntries().contains(p1));
        assertTrue(pa.getAllPhotoEntries().contains(p2));

        r1.addPhotoEntry(p3);

        assertEquals(3, pa.getAllPhotoEntries().size());
        assertTrue(pa.getAllPhotoEntries().contains(p3));
    }
}