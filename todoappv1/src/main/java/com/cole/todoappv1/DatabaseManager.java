package com.cole.todoappv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DEADLINE = "deadline";

    public DatabaseManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // build sql database
        String sqlCreate = "CREATE TABLE " + TABLE_TASK;
        sqlCreate += " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sqlCreate += TITLE + " TEXT, ";
        sqlCreate += DESCRIPTION + " TEXT, ";
        sqlCreate += DEADLINE + " INTEGER)";

        database.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // Drop old table if exists
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Re-create db
        onCreate(database);
    }

    public void insert(Task task) {
        SQLiteDatabase database = this.getWritableDatabase();
        // Creating data to insert into db
        ContentValues values = new ContentValues();
        values.put(TITLE, task.getTitle());
        values.put(DESCRIPTION, task.getDescription());
        values.put(DEADLINE, task.getDeadline().getTime());
        database.insert(TABLE_TASK, null, values);
        database.close();
    }

    public ArrayList<Task> selectAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlQuery, null);

        ArrayList<Task> taskList = new ArrayList<Task>();
        while (cursor.moveToNext()) {
            Task currentTask = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
            taskList.add(currentTask);
        }
        database.close();
        return taskList;
    }

    public Task selectById(int checkedId) {
        String sqlQuery = "SELECT * FROM " + TABLE_TASK;
        sqlQuery += " WHERE " + ID + " = " + checkedId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));

        database.close();
        return task;
    }

    public void deleteById(int checkedId) {
        SQLiteDatabase database =  this.getWritableDatabase();
        database.delete(TABLE_TASK, "id=" + checkedId, null);
        database.close();

    }

    public void updateById(int id, String title, String description, Timestamp deadline) {
        SQLiteDatabase database = this.getWritableDatabase();

        // Creating data to insert into db
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(DESCRIPTION, description);
        values.put(DEADLINE, deadline.getTime());
        database.update(TABLE_TASK, values, "id=" + id, null);
    }
}
