package com.example.woddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PwResetSuccessActivity extends AppCompatActivity {
    TextView emailTextView;
    Button gotoLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_reset_success);

        String getEmail = getIntent().getStringExtra("email");

        emailTextView = findViewById(R.id.email_text_view);
        gotoLoginButton = findViewById(R.id.goto_login_button);

        emailTextView.setText(getEmail);
        gotoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwResetSuccessActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}