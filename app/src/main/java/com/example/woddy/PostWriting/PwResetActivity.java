package com.example.woddy.PostWriting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.R;
import com.google.firebase.auth.FirebaseAuth;

public class PwResetActivity extends AppCompatActivity {
    TextView emailEditText;
    Button pwResetButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_reset);

        emailEditText = findViewById(R.id.email_input_edit_text);
        pwResetButton = findViewById(R.id.pw_reset_button);

        firebaseAuth = FirebaseAuth.getInstance();

        pwResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                if (TextUtils.isEmpty("email")) {
                    Toast.makeText(PwResetActivity.this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).continueWith(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(PwResetActivity.this, "이메일 전송 완료", Toast.LENGTH_LONG).show();
                            //intent 이동
                            Intent intent = new Intent(PwResetActivity.this, PwResetSuccessActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(PwResetActivity.this, "이메일 전송 실패", Toast.LENGTH_LONG).show();

                        }
                        return null;
                    });
                }
            }
        });
    }
}