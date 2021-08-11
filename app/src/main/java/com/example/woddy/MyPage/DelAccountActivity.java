package com.example.woddy.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.woddy.BaseActivity;
import com.example.woddy.R;

public class DelAccountActivity extends BaseActivity {

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_account);
        setMyTitle("회원탈퇴");
    }
}