package com.example.savetrip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DBNAME = "SaveTripDB.db";
    static final int DBVERSION = 3;


    public DatabaseHelper(Context mCtx){
        super(mCtx,DBNAME,null,DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE_TABLE_USER);
        db.execSQL(TripDAO.CREATE_TABLE_TRIP);
        db.execSQL(TransactionDAO.CREATE_TABLE_TRANSACTION);
        db.execSQL(TransactionExchangeDAO.CREATE_TABLE_EXCHANGE);
        db.execSQL(TypeDAO.CREATE_TABLE_TYPE);
        db.execSQL(CategoryDAO.CREATE_TABLE_TRANSACTION_CATEGORIES);

        TypeDAO.insertDefaultTypes(db);
        CategoryDAO.insertDefaultCategories(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UserDAO.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TripDAO.TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS "+TransactionDAO.TABLE_TRANSACTION);
        db.execSQL("DROP TABLE IF EXISTS "+TypeDAO.TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS "+CategoryDAO.TABLE_TRANSACTION_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TransactionExchangeDAO.TABLE_EXCHANGE);
        onCreate(db);
    }
}
