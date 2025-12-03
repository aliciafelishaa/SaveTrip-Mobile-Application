package com.example.savetrip.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savetrip.MainActivity;
import com.example.savetrip.R;
import com.example.savetrip.database.CategoryDAO;
import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.TransactionDAO;
import com.example.savetrip.database.TripDAO;
import com.example.savetrip.database.TypeDAO;
import com.example.savetrip.model.Transaction;
import com.example.savetrip.model.TransactionCategories;
import com.example.savetrip.model.TransactionType;
import com.example.savetrip.model.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    Spinner spType;
    Spinner spCategory;
    EditText etTransName, etDesc, etCurrCode, etAmount, etTransDate;
    Button btnSaveTrans;
    private List<TransactionCategories> categoryList;
    private List<TransactionType> typeList;
    int tripId;

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
        etTransDate.setOnClickListener(v -> showDatePicker(etTransDate));
        tripId = getIntent().getIntExtra("trip_id", -1);
        btnSaveTrans = findViewById(R.id.btnSaveTransaction);
        btnSaveTrans.setOnClickListener(v->onClickAddTrans(v));

        setupTypeSpinner();
        setupCategorySpinner();
    }

    private void setupTypeSpinner() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        typeList = TypeDAO.getTypes(db);

        List<String> typeNames = new ArrayList<>();
        for (TransactionType t : typeList) {
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
        }else if(view.getId() == R.id.btnSaveTransaction){
            DatabaseHelper db = new DatabaseHelper(this);
            TransactionDAO transDAO = new TransactionDAO(db);
            Transaction trans = new Transaction();

            if (transName.isEmpty() || transDesc.isEmpty() ||
                    transBaseCode.isEmpty() || transAmount.isEmpty() ||
                    transDate.isEmpty()) {

                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }
            double amount = Double.parseDouble(transAmount);

            int typeIndex = spType.getSelectedItemPosition();
            int categoryIndex = spCategory.getSelectedItemPosition();

            String typeName = typeList.get(typeIndex).getName(); // "income" / "expense"
            int categoryId = categoryList.get(categoryIndex).getId();  // Ambil ID Kategori

            Transaction transaction = new Transaction();
            transaction.setName(transName);
            transaction.setDescription(transDesc);
            transaction.setCurrencyCode(transBaseCode);
            transaction.setAmount(amount);
            transaction.setTransactionDate(transDate);
            transaction.setType(typeName);
            transaction.setCategoryId(categoryId);
            transaction.setTripId(tripId);

            DatabaseHelper helper = new DatabaseHelper(this);
            TransactionDAO transactionDAO = new TransactionDAO(helper);

            long result = transactionDAO.addTrans(transaction);

            if (result > 0) {
                Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show();

                TripDAO tripDAO = new TripDAO(new DatabaseHelper(this));
                Trip currTrip = tripDAO.getTripById(tripId);  // ambil data trip lengkap
                Intent intent = new Intent(this, DetailViewActivity.class);
                intent.putExtra("trip_id", tripId);   // balikin data trip_id
                intent.putExtra("tripName", currTrip.getTripName());
                intent.putExtra("destination", currTrip.getDestinationCountry());
                intent.putExtra("budget", currTrip.getInitialBudget());
                intent.putExtra("outcome", currTrip.getOutcomeTotalTransaction());
                intent.putExtra("startDate", currTrip.getStartDate());
                intent.putExtra("endDate", currTrip.getEndDate());
                startActivity(intent);

                finish();
            }
            else {
                Toast.makeText(this, "Failed to save transaction", Toast.LENGTH_SHORT).show();
            }
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