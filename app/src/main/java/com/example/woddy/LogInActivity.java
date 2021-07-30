package com.example.woddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {
    EditText idEditText;
    EditText pwEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        idEditText = findViewById(R.id.id_edit_text);
        pwEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
    }
}