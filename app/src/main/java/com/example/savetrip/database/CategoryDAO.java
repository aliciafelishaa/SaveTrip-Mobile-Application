package com.example.savetrip.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.savetrip.model.TransactionCategories;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    static final String TABLE_TRANSACTION_CATEGORIES = "Transaction_categories";

    //CATEGORIES
     static final String CATEGORIES_ID = "id";
     static final String CATEGORIES_NAME = "name";
    static final String CATEGORIES_TYPE_ID = "type_id";

    //field created at
     static final String COLUMN_CREATED_AT = "created_at";


    //CREATE TABLE
    static final String CREATE_TABLE_TRANSACTION_CATEGORIES =
            "CREATE TABLE " + TABLE_TRANSACTION_CATEGORIES + " (" +
                    CATEGORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORIES_NAME + " TEXT NOT NULL UNIQUE, " +
                    CATEGORIES_TYPE_ID + " INTEGER, " +
                    COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY(" + CATEGORIES_TYPE_ID + ") REFERENCES " +
                    TypeDAO.TABLE_TYPE + "(" + TypeDAO.TYPE_ID + ")" +
                    ")";

    //Insert
    public static void insertDefaultCategories(SQLiteDatabase db) {
        db.execSQL("INSERT OR IGNORE INTO " + TABLE_TRANSACTION_CATEGORIES +
                " (name, type_id) VALUES " +
                "('Food', 2)," +
                "('Transport', 2)," +
                "('Shopping', 2)," +
                "('Salary', 1)," +
                "('Bonus', 1)," +
                "('Other', 2)");
    }

    //GetCategory
    public static List<TransactionCategories> getCategories(SQLiteDatabase db) {
        List<TransactionCategories> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_TRANSACTION_CATEGORIES,
               null
        );

        if (cursor.moveToFirst()) {
            do {
                list.add(new TransactionCategories(
                        cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORIES_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CATEGORIES_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORIES_TYPE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }


}
