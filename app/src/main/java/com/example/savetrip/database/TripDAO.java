package com.example.savetrip.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.savetrip.model.Trip;

import java.util.ArrayList;

public class TripDAO {
    static final String TABLE_TRIP = "Trip";
    //TRIP
    static final String TRIP_ID = "trip_id";
    static final String TRIP_USER_ID = "user_id";
    static final String TRIP_NAME = "trip_name";
    static final String TRIP_DESTINATION_COUNTRY = "destination_country";
    static final String TRIP_START_DATE = "start_date";
    static final String TRIP_END_DATE = "end_date";
    static final String TRIP_INITIAL_BUDGET = "initial_budget";
    static final String TRIP_BASE_CURRENCY = "base_currency";
    static final String TRIP_OUTCOME_TOTAL = "outcome_total_transaction";
    static final String TRIP_NOTE = "note";
    static final String COLUMN_CREATED_AT = "created_at";

    static final String CREATE_TABLE_TRIP = "CREATE TABLE " + TABLE_TRIP + "("
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
            + "FOREIGN KEY(" + TRIP_USER_ID + ") REFERENCES " + UserDAO.TABLE_USER + "(" + UserDAO.USER_ID + ")"
            + ")";

    DatabaseHelper dbHelper;
    public TripDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long addTrip(Trip trip){
        long result = -1;
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRIP_USER_ID, trip.getUserId());
        cv.put(TRIP_NAME, trip.getTripName());
        cv.put(TRIP_DESTINATION_COUNTRY, trip.getDestinationCountry());
        cv.put(TRIP_START_DATE, trip.getStartDate());
        cv.put(TRIP_END_DATE, trip.getEndDate());
        cv.put(TRIP_INITIAL_BUDGET, trip.getInitialBudget());
        cv.put(TRIP_BASE_CURRENCY, trip.getBaseCurrency());
        cv.put(TRIP_NOTE, trip.getNote());
        result = db.insert(TABLE_TRIP,null,cv);
        db.close();
        return result;
    }

    public ArrayList<Trip> getTripsByUserId(int userId) {
        ArrayList<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TRIP,
                null,
                TRIP_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );


        if (cursor.moveToFirst()) {
            do {
                Trip trip = new Trip();

                trip.setTripId(cursor.getInt(0));
                trip.setUserId(cursor.getInt(1));
                trip.setTripName(cursor.getString(2));
                trip.setDestinationCountry(cursor.getString(3));
                trip.setStartDate(cursor.getString(4));
                trip.setEndDate(cursor.getString(5));
                trip.setInitialBudget(cursor.getDouble(6));
                trip.setBaseCurrency(cursor.getString(7));
                trip.setOutcomeTotalTransaction(cursor.getDouble(8));
                trip.setNote(cursor.getString(9));
                trip.setCreatedAt(cursor.getString(10));

                trips.add(trip);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return trips;
    }






}
