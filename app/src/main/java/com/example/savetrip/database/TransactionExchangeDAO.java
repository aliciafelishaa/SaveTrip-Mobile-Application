package com.example.savetrip.database;

public class TransactionExchangeDAO {

     static final String TABLE_EXCHANGE = "Trip_exchange_rate";

    //EXCHANGE
     static final String EXCHANGE_ID = "id";
     static final String EXCHANGE_CURRENCY = "currency";
     static final String EXCHANGE_RATE = "rate";

     static final String CREATE_TABLE_EXCHANGE = "CREATE TABLE " + TABLE_EXCHANGE + "("
            + EXCHANGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EXCHANGE_CURRENCY + " TEXT NOT NULL, "
            + EXCHANGE_RATE + " REAL NOT NULL, "
            + ")";

}
