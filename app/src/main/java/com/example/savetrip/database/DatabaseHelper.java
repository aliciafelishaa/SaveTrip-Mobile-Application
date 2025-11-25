package com.example.savetrip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DBNAME = "SaveTripDB.db";
    static final int DBVERSION = 1;

    public static final String TABLE_USER = "User";
    public static final String TABLE_TRIP = "Trip";
    public static final String TABLE_TRANSACTION = "Transactions";
    public static final String TABLE_EXCHANGE = "Trip_exchange_rate";

    //USER
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    //TRIP
    public static final String TRIP_ID = "trip_id";
    public static final String TRIP_USER_ID = "user_id";
    public static final String TRIP_NAME = "trip_name";
    public static final String TRIP_DESTINATION_COUNTRY = "destination_country";
    public static final String TRIP_START_DATE = "start_date";
    public static final String TRIP_END_DATE = "end_date";
    public static final String TRIP_INITIAL_BUDGET = "initial_budget";
    public static final String TRIP_BASE_CURRENCY = "base_currency";
    public static final String TRIP_OUTCOME_TOTAL = "outcome_total_transaction";
    public static final String TRIP_NOTE = "note";

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

    //field created at
    public static final String COLUMN_CREATED_AT = "created_at";
    //EXCHANGE
    public static final String EXCHANGE_ID = "id";
    public static final String EXCHANGE_TRIP_ID = "trip_id";
    public static final String EXCHANGE_FROM_CURRENCY = "from_currency";
    public static final String EXCHANGE_TO_CURRENCY = "to_currency";
    public static final String EXCHANGE_RATE = "rate";

    //CREATE TABLE
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT, "
            + USER_EMAIL + " TEXT UNIQUE, "
            + USER_PASSWORD + " TEXT"
            + ")";

    private static final String CREATE_TABLE_TRIP = "CREATE TABLE " + TABLE_TRIP + "("
            + TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRIP_USER_ID + " INTEGER, "
            + TRIP_NAME + " TEXT, "
            + TRIP_DESTINATION_COUNTRY + " TEXT, "
            + TRIP_START_DATE + " TEXT, "
            + TRIP_END_DATE + " TEXT, "
            + TRIP_INITIAL_BUDGET + " REAL, "
            + TRIP_BASE_CURRENCY + " TEXT NOT NULL, "
            + TRIP_OUTCOME_TOTAL + " REAL DEFAULT 0, "
            + TRIP_NOTE + " TEXT, "
            + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY(" + TRIP_USER_ID + ") REFERENCES " + TABLE_USER + "(" + USER_ID + ")"
            + ")";


    public DatabaseHelper(Context mCtx){

        super(mCtx,DBNAME,null,DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TRIP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRIP);
        onCreate(db);
    }
}
