package com.example.woddy.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woddy.R;

public class SetAccountActivity extends AppCompatActivity {
    Button pwChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_account);

        pwChangeButton = findViewById(R.id.pw_change_button);

        pwChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetAccountActivity.this, BeforeChangePwActivity.class);
                startActivity(intent);
            }
        });
    }
}