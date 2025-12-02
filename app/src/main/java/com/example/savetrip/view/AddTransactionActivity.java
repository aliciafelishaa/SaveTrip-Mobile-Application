package com.example.savetrip.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savetrip.R;
import com.example.savetrip.model.TransactionCategories;

import java.util.ArrayList;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    Spinner spType;
    Spinner spCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        spType = findViewById(R.id.spType);
        spCategory = findViewById(R.id.spCategory);

        setupTypeSpinner();
        setupCategorySpinner();
    }

    private void setupTypeSpinner() {
        List<String> types = new ArrayList<>();
        types.add("Income");
        types.add("Outcome");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                types
        );
        spType.setAdapter(adapter);
    }

    private List<TransactionCategories> categoryList = new ArrayList<>();
    private void setupCategorySpinner() {
        // Data kategori manual
        categoryList.add(new TransactionCategories(1, "Food", "outcome", ""));
        categoryList.add(new TransactionCategories(2, "Transport", "outcome", ""));
        categoryList.add(new TransactionCategories(3, "Shopping", "outcome", ""));
        categoryList.add(new TransactionCategories(4, "Salary", "income", ""));
        categoryList.add(new TransactionCategories(5, "Bonus", "income", ""));
        categoryList.add(new TransactionCategories(6, "Other", "outcome", ""));

        // Hanya tampilkan nama categories di Spinner
        List<String> names = new ArrayList<>();
        for (TransactionCategories c : categoryList) {
            names.add(c.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                names
        );

        spCategory.setAdapter(adapter);
    }




}