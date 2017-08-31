package com.upadhyay.nilay.todoappsqlite;

/**
 * Created by nilay on 8/9/2017.
 */

public class Tag {
    int id;
    String tag_name;

    public int getId() {
        return id;
    }
    // constructors
    public Tag() {

    }
    public Tag(String tag_name) {
        this.tag_name = tag_name;
    }

    public Tag(int id, String tag_name) {
        this.id = id;
        this.tag_name = tag_name;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
