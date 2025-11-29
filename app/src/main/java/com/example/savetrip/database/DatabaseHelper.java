package com.example.savetrip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DBNAME = "SaveTripDB.db";
    static final int DBVERSION = 2;

    public static final String TABLE_TRANSACTION = "Transactions";
    public static final String TABLE_TRANSACTION_CATEGORIES = "Transaction_categories";
    public static final String TABLE_EXCHANGE = "Trip_exchange_rate";


    //TRANSACTION
    public static final String TRANSACTION_ID = "id";
    public static final String TRANSACTION_TRIP_ID = "trip_id";
    public static final String TRANSACTION_CATEGORY_ID = "category_id";
    public static final String TRANSACTION_TYPE = "type";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_CURRENCY_CODE = "currency_code";
    public static final String TRANSACTION_CONVERTED_AMOUNT = "converted_amount";
    public static final String TRANSACTION_DESCRIPTION = "description";
    public static final String TRANSACTION_DATE = "transaction_date";

    //CATEGORIES
    public static final String CATEGORIES_ID = "id";
    public static final String CATEGORIES_NAME = "name";
    public static final String CATEGORIES_TYPE = "type";

    //field created at
    public static final String COLUMN_CREATED_AT = "created_at";
    //EXCHANGE
    public static final String EXCHANGE_ID = "id";
    public static final String EXCHANGE_TRIP_ID = "trip_id";
    public static final String EXCHANGE_FROM_CURRENCY = "from_currency";
    public static final String EXCHANGE_TO_CURRENCY = "to_currency";
    public static final String EXCHANGE_RATE = "rate";

    //CREATE TABLE


    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + "("
            + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRANSACTION_TRIP_ID + " INTEGER NOT NULL, "
            + TRANSACTION_CATEGORY_ID + " INTEGER, "
            + TRANSACTION_TYPE + " TEXT NOT NULL CHECK(" + TRANSACTION_TYPE + " IN ('income','expense')), "
            + TRANSACTION_AMOUNT + " REAL NOT NULL, "
            + TRANSACTION_CURRENCY_CODE + " TEXT NOT NULL, "
            + TRANSACTION_CONVERTED_AMOUNT + " REAL NOT NULL, "
            + TRANSACTION_DESCRIPTION + " TEXT, "
            + TRANSACTION_DATE + " TEXT NOT NULL, "
            + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY(" + TRANSACTION_TRIP_ID + ") REFERENCES " + TripDAO.TABLE_TRIP + "(" + TripDAO.TRIP_ID + "), "
            + "FOREIGN KEY(" + TRANSACTION_CATEGORY_ID + ") REFERENCES " + TripDAO.TABLE_TRIP + "(" + CATEGORIES_ID + ")"
            + ")";

    private static final String CREATE_TABLE_TRANSACTION_CATEGORIES = "CREATE TABLE " + TABLE_TRANSACTION_CATEGORIES + "("
            + CATEGORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CATEGORIES_NAME + " TEXT NOT NULL UNIQUE, "
            + CATEGORIES_TYPE + " TEXT NOT NULL CHECK(" + CATEGORIES_TYPE + " IN ('income','expense')), "
            + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String CREATE_TABLE_EXCHANGE = "CREATE TABLE " + TABLE_EXCHANGE + "("
            + EXCHANGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EXCHANGE_TRIP_ID + " INTEGER NOT NULL, "
            + EXCHANGE_FROM_CURRENCY + " TEXT NOT NULL, "
            + EXCHANGE_TO_CURRENCY + " TEXT NOT NULL, "
            + EXCHANGE_RATE + " REAL NOT NULL, "
            + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY(" + EXCHANGE_TRIP_ID + ") REFERENCES " + TripDAO.TABLE_TRIP + "(" + TripDAO.TRIP_ID + ")"
            + ")";

    public DatabaseHelper(Context mCtx){

        super(mCtx,DBNAME,null,DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE_TABLE_USER);
        db.execSQL(TripDAO.CREATE_TABLE_TRIP);
        db.execSQL(CREATE_TABLE_TRANSACTION);
        db.execSQL(CREATE_TABLE_TRANSACTION_CATEGORIES);
        db.execSQL(CREATE_TABLE_EXCHANGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UserDAO.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TripDAO.TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSACTION_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXCHANGE);

        onCreate(db);
    }
}
