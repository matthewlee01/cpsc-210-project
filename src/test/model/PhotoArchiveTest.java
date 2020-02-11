package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PhotoArchiveTest {

    PhotoArchive pa;
    PhotoRoll r1, r2;
    PhotoEntry p1, p2, p3;

    @BeforeEach
    void runBefore() {
        pa = new PhotoArchive();

        r1 = new PhotoRoll("roll1");
        r2 = new PhotoRoll("roll2");

        p1 = new PhotoEntry(new File("data/tobs.jpg"));
        p2 = new PhotoEntry(new File("data/tobs.jpg"));
        p3 = new PhotoEntry(new File("data/tobs.jpg"));
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

    @Test
    void testFilterPhotosByTag() {

        Tag t1 = new Tag("#amazing");
        Tag t2 = new Tag("#wow");
        p1.addTag(t1);
        p2.addTag(t2);
        p3.addTag(t1);

        ArrayList<PhotoEntry> pl = new ArrayList<>();
        pl.add(p1);
        pl.add(p2);
        pl.add(p3);

        ArrayList<PhotoEntry> plt1 = PhotoArchive.filterPhotosByTag(pl, t1);
        assertTrue(plt1.contains(p1));
        assertFalse(plt1.contains(p2));
        assertTrue(plt1.contains(p3));

        ArrayList<PhotoEntry> plt2 = PhotoArchive.filterPhotosByTag(pl, t2);
        assertFalse(plt2.contains(p1));
        assertTrue(plt2.contains(p2));
        assertFalse(plt2.contains(p3));

    }
}