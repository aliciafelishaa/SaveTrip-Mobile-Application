package com.example.savetrip.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.savetrip.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TypeDAO {

    public static final String TABLE_TYPE = "Transaction_types";
    public static final String TYPE_ID = "id";
    public static final String TYPE_NAME = "name";

    public static final String CREATE_TABLE_TYPE =
            "CREATE TABLE " + TABLE_TYPE + " (" +
                    TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TYPE_NAME + " TEXT NOT NULL UNIQUE)";

    // Insert default types
    public static void insertDefaultTypes(SQLiteDatabase db) {
        db.execSQL("INSERT OR IGNORE INTO " + TABLE_TYPE + " (name) VALUES ('income'), ('expense')");
    }

    // Get all types
    public static List<TransactionType> getTypes(SQLiteDatabase db) {
        List<TransactionType> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TYPE, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new TransactionType(
                        cursor.getInt(cursor.getColumnIndexOrThrow(TYPE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TYPE_NAME))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}
