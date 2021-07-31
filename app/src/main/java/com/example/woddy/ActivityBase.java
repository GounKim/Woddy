package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.woddy.fragment.FragmentChatting;
import com.example.woddy.fragment.FragmentHome;
import com.example.woddy.fragment.FragmentMy;
import com.example.woddy.fragment.FragmentPost;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.android.material.navigation.NavigationBarView;


public class ActivityBase extends AppCompatActivity {
    Toolbar mToolbar;
    TextView toolbarTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }




//    // 툴바 기본 설정
//    @Override
//    public void setContentView(int layoutResID) {
//        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
//        getLayoutInflater().inflate(layoutResID, activityContainer, true);
//        super.setContentView(fullView);
//
//        mToolbar = findViewById(R.id.chatting_room_toolbar);
//        toolbarTitle = findViewById(R.id.toolbar_title);
//
//        // 툴바 사용 여부 결정
//        if(useToolbar()) {
//            setSupportActionBar(mToolbar);
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setDisplayShowCustomEnabled(true);    // 커스터마이징하기
//            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setDisplayHomeAsUpEnabled(true);  // 뒤로가기 버튼
//        } else {
//            mToolbar.setVisibility(View.GONE);
//        }
//    }
//
//    // 툴바 사용여부 (사용 기본)
//    protected boolean useToolbar() {
//        return true;
//    }
//
//    protected void setMyTitle(String title) {
//        toolbarTitle.setText(title);
//    }
//
//    // 앱바 메뉴 클릭
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home: {
//                finish();
//                return true;
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
//https://stickode.tistory.com/16