package com.example.savetrip.database;

public class TransactionExchangeDAO {

     static final String TABLE_EXCHANGE = "Trip_exchange_rate";

    //EXCHANGE
     static final String EXCHANGE_ID = "id";
     static final String EXCHANGE_TRIP_ID = "trip_id";
     static final String EXCHANGE_FROM_CURRENCY = "from_currency";
     static final String EXCHANGE_TO_CURRENCY = "to_currency";
     static final String EXCHANGE_RATE = "rate";

     static final String COLUMN_CREATED_AT = "created_at";


     static final String CREATE_TABLE_EXCHANGE = "CREATE TABLE " + TABLE_EXCHANGE + "("
            + EXCHANGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EXCHANGE_TRIP_ID + " INTEGER NOT NULL, "
            + EXCHANGE_FROM_CURRENCY + " TEXT NOT NULL, "
            + EXCHANGE_TO_CURRENCY + " TEXT NOT NULL, "
            + EXCHANGE_RATE + " REAL NOT NULL, "
            + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY(" + EXCHANGE_TRIP_ID + ") REFERENCES " + TripDAO.TABLE_TRIP + "(" + TripDAO.TRIP_ID + ")"
            + ")";

}
