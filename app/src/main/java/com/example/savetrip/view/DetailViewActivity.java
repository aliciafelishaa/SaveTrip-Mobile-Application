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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.R;
import com.example.savetrip.adapter.TransactionAdapter;
import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.TransactionDAO;
import com.example.savetrip.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DetailViewActivity extends AppCompatActivity {

    TextView tvName, tvDestination, tvBudget, tvOutcome, tvStartDate, tvEndDate;
    Button btnAddTransaction;
    RecyclerView rvTrans;
    TransactionAdapter adapter;
    ArrayList<Transaction> transactionList;

    int tripId;

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
        tripId = intent.getIntExtra("trip_id", -1);

        // Set ke UI
        tvName.setText(name);
        tvDestination.setText("Destination: " + destination);
        tvStartDate.setText("Start: " + startDate);
        tvEndDate.setText("End: " + endDate);
        tvBudget.setText("Budget: Rp " + budget);
        tvOutcome.setText("Outcome: Rp " + outcome);

        //Set ke RV
        rvTrans = findViewById(R.id.rvTransaction);
        rvTrans.setLayoutManager(new LinearLayoutManager(this));

        int tripId = getIntent().getIntExtra("trip_id", -1);
        DatabaseHelper db = new DatabaseHelper(this);
        TransactionDAO dao = new TransactionDAO(db);
        transactionList = dao.getTransactionWithCategoryNameByTripId(tripId);

        // 4. Set Adapter
        adapter = new TransactionAdapter(transactionList);
        rvTrans.setAdapter(adapter);
    }

    int transID;
    public void addTransaction(View view) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        intent.putExtra("trip_id", tripId);
        startActivity(intent);
    }
}