package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;


public class ActivityBase extends AppCompatActivity {
    Toolbar mToolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    // 툴바 기본 설정
    @Override
    public void setContentView(int layoutResID) {
        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        mToolbar = findViewById(R.id.chatting_room_toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        // 툴바 사용 여부 결정
        if(useToolbar()) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);    // 커스터마이징하기
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);  // 뒤로가기 버튼
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    // 툴바 사용여부 (사용 기본)
    protected boolean useToolbar() {
        return true;
    }

    protected void setMyTitle(String title) {
        toolbarTitle.setText(title);
    }

    // 앱바 메뉴 클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}