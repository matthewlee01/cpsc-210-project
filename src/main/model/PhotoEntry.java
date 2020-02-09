package model;

import java.util.Date;
import java.util.LinkedHashSet;

// a photo with its associated data and tags
public class PhotoEntry {

    private String photoFile;
    private Date date;
    private LinkedHashSet<Tag> tags;
    private FilmRoll roll;
    private int iso;

}
