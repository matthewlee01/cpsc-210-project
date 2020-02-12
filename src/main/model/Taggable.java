package model;

import java.util.ArrayList;

public interface Taggable {

    boolean addTag(Tag t);

    boolean removeTag(Tag t);

    ArrayList<Tag> getTags();

}
