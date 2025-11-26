package com.example.savetrip.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.savetrip.model.User;


public class UserDAO {

    static final String TABLE_USER = "User";

    static final String USER_ID = "user_id";
    static final String USER_NAME = "name";
    static final String USER_EMAIL = "email";
    static final String USER_PASSWORD = "password";
    static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT, "
            + USER_EMAIL + " TEXT UNIQUE, "
            + USER_PASSWORD + " TEXT"
            + ")";
    DatabaseHelper dbHelper;

    public UserDAO(DatabaseHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public long addUser(User user){
        long result = -1;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getName());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_PASSWORD, user.getPassword());
        result = db.insert(TABLE_USER,null,cv);
        db.close();
        return result;
    }

    public User checkUser(String email, String password){
        User result = null;
        Cursor cursor = null;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        cursor = db.query(TABLE_USER,
                null,
                USER_EMAIL+"=? AND "+USER_PASSWORD+"=?",
                new String[]{email, password}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            result = new User();
            result.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)));
            result.setName(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)));
            result.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
            result.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(USER_PASSWORD)));
        }
        return result;
    }

}
