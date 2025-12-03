package com.example.savetrip.database;

import android.annotation.SuppressLint;
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
    static final String TRANSACTION_NAME = "name";
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
            + TRANSACTION_NAME + " TEXT, "
            + TRANSACTION_CATEGORY_ID + " INTEGER, "
            + TRANSACTION_TYPE + " TEXT NOT NULL CHECK(" + TRANSACTION_TYPE + " IN ('income','expense')), "
            + TRANSACTION_AMOUNT + " REAL NOT NULL, "
            + TRANSACTION_CURRENCY_CODE + " TEXT NOT NULL, "
            + TRANSACTION_CONVERTED_AMOUNT + " REAL NOT NULL, "
            + TRANSACTION_DESCRIPTION + " TEXT, "
            + TRANSACTION_DATE + " TEXT NOT NULL, "
            + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY(" + TRANSACTION_TRIP_ID + ") REFERENCES " + TripDAO.TABLE_TRIP + "(" + TripDAO.TRIP_ID + "), "
            + "FOREIGN KEY(" + TRANSACTION_CATEGORY_ID + ") REFERENCES " + CategoryDAO.TABLE_TRANSACTION_CATEGORIES + "(" + CATEGORIES_ID + ")"
            + ")";

    DatabaseHelper dbHelper;
    public TransactionDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long addTrans(Transaction trans){
        long result = -1;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_TRIP_ID, trans.getTripId());
        cv.put(TRANSACTION_CATEGORY_ID, trans.getCategoryId());
        cv.put(TRANSACTION_NAME, trans.getName());
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
                transc.setName(cursor.getString(2));
                transc.setCategoryId(cursor.getInt(3));
                transc.setType(cursor.getString(4));
                transc.setAmount(cursor.getDouble(5));
                transc.setCurrencyCode(cursor.getString(6));
                transc.setConvertedAmount(cursor.getDouble(7));
                transc.setDescription(cursor.getString(8));
                transc.setTransactionDate(cursor.getString(9));
                transc.setCreatedAt(cursor.getString(10));

                trans.add(transc);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return trans;
    }

    @SuppressLint("Range")
    public ArrayList<Transaction> getTransactionWithCategoryNameByTripId(int tripId){
        ArrayList<Transaction> trans = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t.*, c.name AS category_name " +
                "FROM " + TABLE_TRANSACTION + " t " +
                "LEFT JOIN " + CategoryDAO.TABLE_TRANSACTION_CATEGORIES + " c " +
                "ON t." + TRANSACTION_CATEGORY_ID + " = c." + CATEGORIES_ID + " " +
                "WHERE t." + TRANSACTION_TRIP_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tripId)});

        if(cursor.moveToFirst()){
            do{
                Transaction transc = new Transaction();
                transc.setId(cursor.getInt(cursor.getColumnIndex(TRANSACTION_ID)));
                transc.setTripId(cursor.getInt(cursor.getColumnIndex(TRANSACTION_TRIP_ID)));
                transc.setName(cursor.getString(cursor.getColumnIndex(TRANSACTION_NAME)));
                transc.setCategoryId(cursor.getInt(cursor.getColumnIndex(TRANSACTION_CATEGORY_ID)));
                transc.setCategoryName(cursor.getString(cursor.getColumnIndex("category_name"))); // baru
                transc.setType(cursor.getString(cursor.getColumnIndex(TRANSACTION_TYPE)));
                transc.setAmount(cursor.getDouble(cursor.getColumnIndex(TRANSACTION_AMOUNT)));
                transc.setCurrencyCode(cursor.getString(cursor.getColumnIndex(TRANSACTION_CURRENCY_CODE)));
                transc.setConvertedAmount(cursor.getDouble(cursor.getColumnIndex(TRANSACTION_CONVERTED_AMOUNT)));
                transc.setDescription(cursor.getString(cursor.getColumnIndex(TRANSACTION_DESCRIPTION)));
                transc.setTransactionDate(cursor.getString(cursor.getColumnIndex(TRANSACTION_DATE)));
                transc.setCreatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT)));

                trans.add(transc);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return trans;
    }

    @SuppressLint("Range")
    public double getTotalExpenseByTripId(int tripId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double total = 0;

        String query = "SELECT SUM(amount) as totalExpense FROM " + TABLE_TRANSACTION +
                " WHERE trip_id = ? AND type = 'expense'";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tripId)});
        if(cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndex("totalExpense"));
        }
        cursor.close();
        return total;
    }


}
