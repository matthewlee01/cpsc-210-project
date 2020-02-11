package model;

// tags used to categorize and filter photo entries
public class Tag {

    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }

    // EFFECTS: returns the tag string for this tag
    public String getTag() {
        return tag;
    }

}
