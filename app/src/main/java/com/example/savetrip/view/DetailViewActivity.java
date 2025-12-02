package com.example.savetrip.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savetrip.R;

public class DetailViewActivity extends AppCompatActivity {

    TextView tvName, tvDestination, tvBudget, tvOutcome, tvStartDate, tvEndDate;
    Button btnAddTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        // Bind UI
        tvName = findViewById(R.id.tvName);
        tvDestination = findViewById(R.id.tvDestination);
        tvBudget = findViewById(R.id.tvBudget);
        tvOutcome = findViewById(R.id.tvOutcome);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String name = intent.getStringExtra("tripName");
        String destination = intent.getStringExtra("destination");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        double budget = intent.getDoubleExtra("budget", 0);
        double outcome = intent.getDoubleExtra("outcome", 0);

        // Set ke UI
        tvName.setText(name);
        tvDestination.setText("Destination: " + destination);
        tvStartDate.setText("Start: " + startDate);
        tvEndDate.setText("End: " + endDate);
        tvBudget.setText("Budget: Rp " + budget);
        tvOutcome.setText("Outcome: Rp " + outcome);
    }

    int transID;
    public void addTransaction(View view) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        intent.putExtra("TRANSACTION_ID", transID);
        startActivity(intent);
    }
}