package com.upadhyay.nilay.todoappsqlite;

import static android.R.attr.priority;

/**
 * Created by nilay on 8/9/2017.
 */

public class Todo {
    int id;
    String note;
    String status;
    String created_at;
    String priority;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // constructors
    public Todo() {
    }

    public Todo(int id,String note,String created_at,String status,String priority) {
        this.id = id;
        this.note = note;
        this.status = status;
        this.created_at = created_at;
        this.priority = priority;
    }
    public Todo(String note,String created_at,String status,String priority) {
        this.note = note;
        this.status = status;
        this.created_at = created_at;
        this.priority = priority;
    }
    public void setId(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
