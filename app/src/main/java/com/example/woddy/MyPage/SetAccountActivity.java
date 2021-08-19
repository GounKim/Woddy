package com.example.woddy.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.woddy.BaseActivity;
import com.example.woddy.R;

public class SetAccountActivity extends BaseActivity {
    Button pwChangeButton;
    ImageView toolbarLogoImage;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_account);
        setMyTitle("계정 관리");

        toolbarLogoImage.findViewById(R.id.toolbar_logo);
        toolbarLogoImage.setVisibility(View.GONE);

        pwChangeButton = findViewById(R.id.pw_change_button);

        pwChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetAccountActivity.this, ChangePwActivity.class);
                startActivity(intent);
            }
        });
    }
}