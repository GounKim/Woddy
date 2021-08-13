package com.example.woddy;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Woddy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

}