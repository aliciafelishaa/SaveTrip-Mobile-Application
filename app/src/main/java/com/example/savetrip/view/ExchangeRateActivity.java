package com.example.savetrip.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savetrip.R;
import com.example.savetrip.adapter.ExchangeRateAdapter;
import com.example.savetrip.helper.CurrencyConverter;
import com.example.savetrip.model.TripExchangeRate;

import java.util.ArrayList;
import java.util.Map;

public class ExchangeRateActivity extends AppCompatActivity {
    RecyclerView rvExchangeRate;
    ExchangeRateAdapter adapter;
    ArrayList<TripExchangeRate> rates;
    private static final String API_KEY = "API_KEY";
    private CurrencyConverter converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);

        rvExchangeRate = findViewById(R.id.rvExchangeRate);
        rvExchangeRate.setLayoutManager(new LinearLayoutManager(this));

        rates = new ArrayList<>();
        adapter = new ExchangeRateAdapter(rates);
        rvExchangeRate.setAdapter(adapter);

        converter = new CurrencyConverter(this, API_KEY);
        fetchAllRates();
    }

    private void fetchAllRates() {
        String baseCurrency = "IDR";

        converter.fetchAllRates(baseCurrency,
                () -> {
                    rates.clear();
                    for (Map.Entry<String, Double> entry : converter.latestRates.entrySet()) {
                        rates.add(new TripExchangeRate(entry.getKey(), entry.getValue()));
                    }
                    adapter.notifyDataSetChanged();
                },
                () -> {
                    Toast.makeText(this, "Failed to fetch exchange rates", Toast.LENGTH_SHORT).show();
                });
    }
}