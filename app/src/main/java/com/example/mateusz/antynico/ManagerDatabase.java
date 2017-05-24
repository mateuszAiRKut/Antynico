package com.example.mateusz.antynico;

/**
 * Created by Mateusz on 09.05.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ManagerDatabase extends SQLiteOpenHelper{

    public ManagerDatabase(Context context) {
        super(context, "antynico.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table uzytkownicy(" +
                        "nr integer primary key autoincrement," +
                        "Email text," +
                        "Password text);" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddUser(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("Password", password);
        db.insertOrThrow("uzytkownicy", null, values);

    }

    public Cursor ViewAllUsers() {
        String[] columns = {"nr", "Email", "Password"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("uzytkownicy", columns, null, null, null, null, null);
        return cursor;
    }

    public void RemoveUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {""+id};
        db.delete("uzytkownicy", "nr=?", args);
    }
}
