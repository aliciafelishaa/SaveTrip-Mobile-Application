package com.example.savetrip.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savetrip.MainActivity;
import com.example.savetrip.R;
import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.TripDAO;
import com.example.savetrip.model.Trip;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etTripName, etDestination, etBaseCurrency, etStartDate, etEndDate, etInitialBudget, etNote;
    Button btnSaveTrip;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        userId = getIntent().getIntExtra("USER_ID", -1);

        etTripName = findViewById(R.id.etTripName);
        etDestination = findViewById(R.id.etDestination);
        etBaseCurrency = findViewById(R.id.etBaseCurrency);
        etStartDate = findViewById(R.id.etStartDate);
        etStartDate.setOnClickListener(this);
        etEndDate = findViewById(R.id.etEndDate);
        etEndDate.setOnClickListener(this);
        etInitialBudget = findViewById(R.id.etInitialBudget);
        etNote = findViewById(R.id.etNote);
        btnSaveTrip = findViewById(R.id.btnSaveTrip);
        btnSaveTrip.setOnClickListener(this);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onClick(View v) {
        String strName = etTripName.getText().toString();
        String strDestination = etDestination.getText().toString();
        String strStartDate = etStartDate.getText().toString();
        String strEndDate = etEndDate.getText().toString();
        String strBudget = etInitialBudget.getText().toString();
        String strBaseCurrency = etBaseCurrency.getText().toString();
        String strNote = etNote.getText().toString();

        if(v.getId()==R.id.etStartDate){
            showDatePicker(etStartDate);
        }else if(v.getId()==R.id.etEndDate){
            showDatePicker(etEndDate);
        }else if(v.getId()==R.id.btnSaveTrip){
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            TripDAO tripDao = new TripDAO(dbHelper);
            Trip trip = new Trip();

            if (strName.isEmpty() || strDestination.isEmpty() ||
                    strStartDate.isEmpty() || strEndDate.isEmpty() ||
                    strBaseCurrency.isEmpty()) {

                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, MainActivity.class);
            trip.setUserId(userId);
            trip.setTripName(strName);
            trip.setDestinationCountry(strDestination);
            trip.setStartDate(strStartDate);
            trip.setEndDate(strEndDate);
            trip.setInitialBudget(Double.parseDouble(strBudget));
            trip.setBaseCurrency(strBaseCurrency);
            trip.setNote(strNote);

            long result = tripDao.addTrip(trip);
            finish();

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