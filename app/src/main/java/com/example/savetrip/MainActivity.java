package com.example.savetrip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.adapter.TripAdapter;
import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.TripDAO;
import com.example.savetrip.model.Trip;
import com.example.savetrip.view.AddTripActivity;
import com.example.savetrip.view.ExchangeRateActivity;
import com.example.savetrip.view.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtGreeting;
    Button btnAddTrip, btnLogOut, btnDetail, btnDetailExchangeRate;
    TripAdapter tripAdapter;
    RecyclerView rvTrip;
    ArrayList<Trip> trips;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtGreeting = findViewById(R.id.txtGreeting);
        btnDetailExchangeRate = findViewById(R.id.btnUpdateRates);
        btnDetailExchangeRate.setOnClickListener(this);
        btnAddTrip = findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(this);
        btnLogOut = findViewById(R.id.logOutBtn);
        btnLogOut.setOnClickListener(v -> logOut());

        SharedPreferences sp = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String username = sp.getString("username", "User");
        userId = sp.getInt("user_id", -1);

        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        TripDAO tripDAO = new TripDAO(dbHelper);
        txtGreeting.setText("Hi, " + username + " 👋");

        rvTrip = findViewById(R.id.rvTrip);
        rvTrip.setLayoutManager(new LinearLayoutManager(this));

        trips = new ArrayList<>();
        for (Trip t : tripDAO.getTripsByUserId(userId)) {
            Trip fullTrip = tripDAO.getTripWithExpenseTotalById(t.getTripId());
            trips.add(fullTrip);
        }
        tripAdapter = new TripAdapter(this, trips);
        rvTrip.setAdapter(tripAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddTrip) {
            Intent intent = new Intent(this, AddTripActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        } else if (v.getId() == R.id.btnUpdateRates) {
            Intent intent = new Intent(this, ExchangeRateActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        TripDAO tripDAO = new TripDAO(dbHelper);

        trips = tripDAO.getTripsByUserId(userId);

        tripAdapter = new TripAdapter(this,trips);
        rvTrip.setAdapter(tripAdapter);

        tripAdapter.notifyDataSetChanged();
    }

    public void logOut(){
        SharedPreferences sp = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}