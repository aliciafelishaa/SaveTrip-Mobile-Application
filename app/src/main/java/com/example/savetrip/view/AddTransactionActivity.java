package com.example.savetrip.view;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savetrip.R;
import com.example.savetrip.database.CategoryDAO;
import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.TypeDAO;
import com.example.savetrip.model.TransactionCategories;
import com.example.savetrip.model.TransactionType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    Spinner spType;
    Spinner spCategory;
    EditText etTransName, etDesc, etCurrCode, etAmount, etTransDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        spType = findViewById(R.id.spType);
        spCategory = findViewById(R.id.spCategory);
        etTransName = findViewById(R.id.etTransName);
        etDesc = findViewById(R.id.etTransDescription);
        etCurrCode = findViewById(R.id.etTransCurrency);
        etAmount = findViewById(R.id.etAmount);
        etTransDate = findViewById(R.id.etTransactionDate);

        setupTypeSpinner();
        setupCategorySpinner();
    }

    private void setupTypeSpinner() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<TransactionType> types = TypeDAO.getTypes(db);

        List<String> typeNames = new ArrayList<>();
        for (TransactionType t : types) {
            typeNames.add(t.getName());
        }

        //Masukkan data ke Spinnernya
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                typeNames
        );
        spType.setAdapter(adapter);
    }

    private List<TransactionCategories> categoryList;
    private void setupCategorySpinner() {
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        categoryList = CategoryDAO.getCategories(db);

        List<String> names = new ArrayList<>();
        for (TransactionCategories c:categoryList) {
            names.add(c.getName());
        }

        //Masukkan ke data Spinnernya
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                names
        );

        spCategory.setAdapter(adapter);
        Log.d("CATEGORY_DEBUG", "Total categories = " + categoryList.size());

    }


    public void onClickAddTrans(View view) {
        String transName = etTransName.getText().toString();
        String transDesc = etDesc.getText().toString();
        String transBaseCode = etCurrCode.getText().toString();
        String transAmount = etAmount.getText().toString();
        String transDate = etTransDate.getText().toString();

        if(view.getId()== R.id.etStartDate){
            showDatePicker(etTransDate);
        }else if(view.getId() == R.id.btnAddTransaction){
            DatabaseHelper db = new DatabaseHelper(this);

        }
    }

    private void showDatePicker(EditText targetField) {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    String formattedDate = day + "/" + (month + 1) + "/" + year;
                    targetField.setText(formattedDate);
                }, y, m, d
        );
        dialog.show();
    }
}