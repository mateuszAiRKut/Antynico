package com.example.mateusz.antynico.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;

import com.example.mateusz.antynico.model.User;

/**
 * Created by Mateusz on 23.05.2017.
 */

public class ManageDatabase extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "Antynico.db";
    private static final String tableUser = "user";

    private static final String columnUserId = "userId";
    private static final String columnUserName = "userName";
    private static final String columnUserEmail = "userEmail";
    private static final String columnUserPassword = "userPassword";

    private String createTableUser = "CREATE TABLE " + tableUser + "(" +
            columnUserId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            columnUserName + " TEXT," +
            columnUserEmail + " TEXT," +
            columnUserPassword + " TEXT)";
    private String dropTableUser = "DROP TABLE IF EXISTS " + tableUser;

    public ManageDatabase(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableUser);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnUserName, user.getName());
        values.put(columnUserEmail, user.getEmail());
        values.put(columnUserPassword, user.getPassword());

        // Inserting Row
        db.insert(tableUser, null, values);
        db.close();
    }

    public List<User> getAllUser() {
        String[] columns = {
                columnUserId,
                columnUserEmail,
                columnUserName,
                columnUserPassword
        };
        String sortOrder =
                columnUserName + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableUser,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);


        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(columnUserId))));
                user.setName(cursor.getString(cursor.getColumnIndex(columnUserName)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(columnUserEmail)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(columnUserPassword)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(columnUserName, user.getName());
        values.put(columnUserEmail, user.getEmail());
        values.put(columnUserPassword, user.getPassword());
        db.update(tableUser, values, columnUserId + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableUser, columnUserId + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email) {

        String[] columns = {
                columnUserId
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = columnUserEmail + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(tableUser,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {

        String[] columns = {
                columnUserId
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = columnUserEmail + " = ?" + " AND " + columnUserPassword + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(tableUser,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}
