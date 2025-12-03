package com.example.savetrip.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.savetrip.model.Transaction;
import com.example.savetrip.model.Trip;

import java.util.ArrayList;

public class TransactionDAO {

    static final String TABLE_TRANSACTION = "Transactions";

    //TRANSACTION
     static final String TRANSACTION_ID = "id";
     static final String TRANSACTION_TRIP_ID = "trip_id";
     static final String TRANSACTION_CATEGORY_ID = "category_id";
     static final String TRANSACTION_TYPE = "type";
     static final String TRANSACTION_AMOUNT = "amount";
     static final String TRANSACTION_CURRENCY_CODE = "currency_code";
     static final String TRANSACTION_CONVERTED_AMOUNT = "converted_amount";
     static final String TRANSACTION_DESCRIPTION = "description";
     static final String TRANSACTION_DATE = "transaction_date";
     static final String COLUMN_CREATED_AT = "created_at";
     static final String CATEGORIES_ID = "id";

    static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + "("
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

    DatabaseHelper dbHelper;
    public TransactionDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long addTrans(Transaction trans){
        long result = -1;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_ID, trans.getId());
        cv.put(TRANSACTION_TRIP_ID, trans.getTripId());
        cv.put(CATEGORIES_ID, trans.getCategoryId());
        cv.put(TRANSACTION_TYPE, trans.getType());
        cv.put(TRANSACTION_AMOUNT, trans.getAmount());
        cv.put(TRANSACTION_CURRENCY_CODE, trans.getCurrencyCode());
        cv.put(TRANSACTION_CONVERTED_AMOUNT, trans.getConvertedAmount());
        cv.put(TRANSACTION_DESCRIPTION, trans.getDescription());
        cv.put(TRANSACTION_DATE, trans.getTransactionDate());
        result = db.insert(TABLE_TRANSACTION, null, cv);
        db.close();
        return result;

    }

    public  ArrayList<Transaction> getTransactionByTripId(int tripId){
        ArrayList<Transaction> trans = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRANSACTION, null,
                TRANSACTION_TRIP_ID + " = ?",
                new String[]{String.valueOf(tripId)},
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            do{
                Transaction transc = new Transaction();

                transc.setId(cursor.getInt(0));
                transc.setTripId(cursor.getInt(1));
                transc.setCategoryId(cursor.getInt(2));
                transc.setType(cursor.getString(3));
                transc.setAmount(cursor.getDouble(4));
                transc.setCurrencyCode(cursor.getString(5));
                transc.setConvertedAmount(cursor.getDouble(6));
                transc.setDescription(cursor.getString(7));
                transc.setTransactionDate(cursor.getString(8));
                transc.setCreatedAt(cursor.getString(9));

                trans.add(transc);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return trans;
    }
}
