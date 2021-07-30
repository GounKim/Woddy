package com.example.woddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyPage extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        setMyTitle("마이페이지");
    }
}