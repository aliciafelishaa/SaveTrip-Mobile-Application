package com.example.savetrip.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.R;
import com.example.savetrip.adapter.TransactionAdapter;
import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.TransactionDAO;
import com.example.savetrip.helper.CurrencyConverter;
import com.example.savetrip.model.Transaction;

import java.util.ArrayList;

public class DetailViewActivity extends AppCompatActivity {

    TextView tvName, tvDestination, tvBudget, tvOutcome, tvStartDate, tvEndDate;
    Button btnAddTransaction;
    RecyclerView rvTrans;
    TransactionAdapter adapter;
    ArrayList<Transaction> transactionList;
    Spinner spinnerCurrency;

    int tripId;
    double initialBudget, totalOutcome;
    String baseCurrency = "IDR";
    String apiKey = "API_KEY";
    CurrencyConverter currencyConverter;

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
        spinnerCurrency = findViewById(R.id.spinnerCurrency);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        currencyConverter = new CurrencyConverter(this, apiKey);

        // Ambil data Intent
        Intent intent = getIntent();
        tripId = intent.getIntExtra("trip_id", -1);
        String name = intent.getStringExtra("tripName");
        String destination = intent.getStringExtra("destination");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        initialBudget = intent.getDoubleExtra("budget", 0);

        // Ambil data transaksi dari DB
        DatabaseHelper db = new DatabaseHelper(this);
        TransactionDAO dao = new TransactionDAO(db);
        transactionList = dao.getTransactionWithCategoryNameByTripId(tripId);
        totalOutcome = dao.getTotalExpenseByTripId(tripId);

        // Set UI
        tvName.setText(name);
        tvDestination.setText("Destination: " + destination);
        tvStartDate.setText("Start: " + startDate);
        tvEndDate.setText("End: " + endDate);

        // RecyclerView
        rvTrans = findViewById(R.id.rvTransaction);
        rvTrans.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(transactionList);
        rvTrans.setAdapter(adapter);

        // Spinner
        String[] currencies = {"IDR", "USD", "SGD", "EUR"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, currencies);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapterSpinner);

        // Ambil latest rates terlebih dahulu
        currencyConverter.fetchLatestRates(baseCurrency, totalOutcome, currencies,
                () -> updateCurrencyViews(baseCurrency), // Success → tampilkan default
                () -> { // Gagal ambil rates
                    tvBudget.setText("Budget: Error fetching rates");
                    tvOutcome.setText("Outcome: Error fetching rates");
                }
        );

        spinnerCurrency.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String targetCurrency = currencies[position];
                updateCurrencyViews(targetCurrency);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });
    }

    public void addTransaction(View view) {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        intent.putExtra("trip_id", tripId);
        startActivity(intent);
    }

    private void updateCurrencyViews(String targetCurrency) {
        currencyConverter.convert(initialBudget, targetCurrency, new CurrencyConverter.ConversionCallback() {
            @Override
            public void onSuccess(double convertedAmount) {
                tvBudget.setText("Budget: " + CurrencyConverter.format(convertedAmount, targetCurrency));
            }

            @Override
            public void onFailure(Exception e) {
                tvBudget.setText("Budget: Error");
            }
        });

        currencyConverter.convert(totalOutcome, targetCurrency, new CurrencyConverter.ConversionCallback() {
            @Override
            public void onSuccess(double convertedAmount) {
                tvOutcome.setText("Outcome: " + CurrencyConverter.format(convertedAmount, targetCurrency));
            }

            @Override
            public void onFailure(Exception e) {
                tvOutcome.setText("Outcome: Error");
            }
        });
    }
}
