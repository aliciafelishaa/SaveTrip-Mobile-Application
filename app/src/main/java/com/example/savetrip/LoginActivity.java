package com.example.savetrip;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;
    UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();

        if(v.getId()==R.id.btnLogin){
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            UserDAO userDao = new UserDAO(dbHelper);
            User user = userDao.checkUser(strEmail,strPassword);
            if(user.getEmail()!=null){
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                SharedPreferences sp = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("user_id", user.getUserId());
                editor.putString("username", user.getName());
                editor.apply();
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"no such user", Toast.LENGTH_LONG).show();
            }
        }else if(v.getId()==R.id.tvRegister){
            startActivity(new Intent(this, RegisterActivity.class));
        }

    }
}