package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagTest {

    Tag t;

    @BeforeEach
    void runBefore() {
        t = new Tag("#selfie");
    }

    @Test
    void testGetTag() {
        assertEquals("#seflie", t.getTag());
    }
}
