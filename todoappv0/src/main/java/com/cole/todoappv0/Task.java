package com.cole.todoappv0;

import java.sql.Timestamp;

public class Task {
    private int id;
    private String title;
    private String description;
    private Timestamp deadline;

    public Task() {
    }

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "(" + id + ", " + title + ", " + description + ")";
    }
}
