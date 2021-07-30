package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.util.function.BinaryOperator;


public class ActivityBase extends AppCompatActivity {
    Toolbar mToolbar;
    TextView toolbarTitle;

    //bottomnavigation
    BottomNavigationView bottomNavigationView;

    private String TAG = "메인";

    //프래그먼트 변수
    Fragment fragment_home;
    Fragment fragment_post;
    Fragment fragment_chatting;
    Fragment fragment_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //프래그먼트 생성
        fragment_home = new FragmentHome();
        fragment_post = new FragmentPost();
        fragment_chatting = new FragmentChatting();
        fragment_my = new FragmentMy();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "바텀 네비게이션 클릭");
                int id = item.getItemId();
                switch(id){
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_post:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,fragment_post).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_chatting:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,fragment_chatting).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_my_page:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,fragment_my).commitAllowingStateLoss();
                        return true;

                }
                return true;
            }
        });
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