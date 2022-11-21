package com.cole.friendmanagerv0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "friendDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FRIEND = "friend";
    private static final String ID = "id";
    private static final String FNAME = "fname";
    private static final String LNAME = "lname";
    private static final String EMAIL = "email";

    public DatabaseManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // build sql database
        String sqlCreate = "CREATE TABLE " + TABLE_FRIEND;
        sqlCreate += " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sqlCreate += FNAME + " TEXT, ";
        sqlCreate += LNAME + " TEXT, ";
        sqlCreate += EMAIL + " TEXT)";

        database.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // Drop old table if exists
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        // Re-create db
        onCreate(database);
    }

    public void insert(Friend friend) {
        SQLiteDatabase database = this.getWritableDatabase();
        // Creating data to insert into db
        ContentValues values = new ContentValues();
        values.put(FNAME, friend.getfName());
        values.put(LNAME, friend.getlName());
        values.put(EMAIL, friend.getEmail());

        database.insert(TABLE_FRIEND, null, values);
        database.close();
    }

    public ArrayList<Friend> selectAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_FRIEND;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlQuery, null);

        ArrayList<Friend> friendList = new ArrayList<Friend>();
        while (cursor.moveToNext()) {
            Friend currentFriend = new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            friendList.add(currentFriend);
        }
        database.close();
        return friendList;
    }

    public Friend selectById(int checkedId) {
        String sqlQuery = "SELECT * FROM " + TABLE_FRIEND;
        sqlQuery += " WHERE " + ID + " = " + checkedId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        Friend friend = new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        database.close();
        return friend;
    }

    public void deleteById(int checkedId) {
        SQLiteDatabase database =  this.getWritableDatabase();
        database.delete(TABLE_FRIEND, "id=" + checkedId, null);
        database.close();

    }

    public void updateById(int id, String fname, String lname, String email) {
        SQLiteDatabase database = this.getWritableDatabase();

        // Creating data to insert into db
        ContentValues values = new ContentValues();
        values.put(FNAME, fname);
        values.put(LNAME, lname);
        values.put(EMAIL, email);
        database.update(TABLE_FRIEND, values, "id=" + id, null);
    }
}
