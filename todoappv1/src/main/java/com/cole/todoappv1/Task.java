package com.cole.todoappv1;

import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private Timestamp deadline;

    public Task() {
    }

    public Task(int id, String title, String description, long timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = new Timestamp(timestamp);
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

    public Timestamp getDeadline() {
        return deadline;
    }

    public String getDeadlineString()
    {
        Date date = new Date();
        date.setTime(deadline.getTime());
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
        return formattedDate;
    }

    public void setDeadline(long deadline) {
        this.deadline = new Timestamp(deadline);
    }

    public void setDeadline(String deadlineString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = formatter.parse(deadlineString);
            Timestamp timestamp = new Timestamp(date.getTime());
            this.deadline = timestamp;
        } catch(Exception e) {
            Log.e("setDeadline", deadlineString + " is invalid");
        }
    }

    public String toString() {
        return "(" + id + ", " + title + ", " + description + ", " + deadline + ")";
    }
}
