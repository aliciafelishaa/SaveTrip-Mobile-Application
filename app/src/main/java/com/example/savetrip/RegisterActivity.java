package com.example.savetrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savetrip.database.DatabaseHelper;
import com.example.savetrip.database.UserDAO;
import com.example.savetrip.model.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword, etEmail;
    Button btnRegister;
    TextView tvLogin;
    UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String strUsername = etUsername.getText().toString();
        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();

        if(v.getId()==R.id.btnRegister){
            if(strUsername.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            userDao = new UserDAO(dbHelper);
            User user = new User();
            user.setName(strUsername);
            user.setEmail(strEmail);
            user.setPassword(strPassword);
            long result = userDao.addUser(user);
            if(result > 0){
                Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==R.id.tvLogin){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}