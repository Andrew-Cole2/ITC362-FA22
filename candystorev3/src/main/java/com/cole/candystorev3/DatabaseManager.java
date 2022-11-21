package com.cole.candystorev3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "candyDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CANDY = "candy";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    public DatabaseManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // build sql database
        String sqlCreate = "CREATE TABLE " + TABLE_CANDY;
        sqlCreate += " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sqlCreate += NAME + " TEXT, ";
        sqlCreate += PRICE + " REAL )";

        database.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // Drop old table if exists
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDY);
        // Re-create db
        onCreate(database);
    }

    public void insert(Candy candy) {
        SQLiteDatabase database = this.getWritableDatabase();
        String sqlInsert = "INSERT INTO " + TABLE_CANDY;
        sqlInsert += " (" + NAME + ", " + PRICE + ") ";
        sqlInsert += "VALUES ('" + candy.getName() + "', '" + candy.getPrice() + "') ";

        database.execSQL(sqlInsert);
        database.close();
    }

    public ArrayList<Candy> selectAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_CANDY;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlQuery, null);

        ArrayList<Candy> candyList = new ArrayList<Candy>();
        while (cursor.moveToNext()) {
            Candy currentCandy = new Candy(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
            candyList.add(currentCandy);
        }
        database.close();
        return candyList;
    }

    public void deleteById(int checkedId) {
        SQLiteDatabase database =  this.getWritableDatabase();
        String sqlDelete = "DELETE FROM " + TABLE_CANDY;
        sqlDelete += " WHERE " + ID + " = " + checkedId;

        database.execSQL(sqlDelete);
        database.close();

    }
}
