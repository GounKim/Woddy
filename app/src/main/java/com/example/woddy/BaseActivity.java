package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woddy.Alarm.AlarmActivity;
import com.example.woddy.Chatting.ChattingFragment;
import com.example.woddy.Home.HomeFragment;
import com.example.woddy.Info.InfoBoardMain;
import com.example.woddy.MyPage.MyPageFragment;
import com.example.woddy.PostBoard.PostBoardMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;


public class BaseActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView toolbarTitle;
    BottomNavigationView bottomNavi;
    ImageView toolbarLogo;

    private Boolean useToolbar = true;
    private Boolean useBottomNavi = true;
    private Boolean useBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        registerPushToken();
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
        toolbarLogo = findViewById(R.id.toolbar_logo);
        bottomNavi = findViewById(R.id.bottomNavi);
        ;

        // 툴바 사용 여부 결정
        if (useToolbar()) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);    // 커스터마이징하기
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(useBackButton);  // 뒤로가기 버튼
        } else {
            mToolbar.setVisibility(View.GONE);
        }

        // BottomNavi 사용 여부
        if (useBottomNavi()) {
            // 첫 화면 설정
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new HomeFragment()).commit();
            setMyTitle("홈");
            toolbarLogo.setVisibility(View.VISIBLE);
            toolbarTitle.setVisibility(View.GONE);

            // BottomNavigationView 내부 아이템 설정
            bottomNavi.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_menu_home:
                            toolbarLogo.setVisibility(View.VISIBLE);
                            toolbarTitle.setVisibility(View.GONE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new HomeFragment()).commit();
                            mToolbar.setVisibility(View.VISIBLE);
                            setMyTitle("홈");
                            return true;
                        case R.id.bottom_menu_post:
                            toolbarLogo.setVisibility(View.GONE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new PostBoardMain()).commit();
                            mToolbar.setVisibility(View.GONE);
                            return true;
                        case R.id.bottom_menu_info:
                            toolbarLogo.setVisibility(View.GONE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new InfoBoardMain()).commit();
                            mToolbar.setVisibility(View.GONE);
                            return true;
                        case R.id.bottom_menu_chatting:
                            toolbarLogo.setVisibility(View.GONE);
                            toolbarTitle.setVisibility(View.VISIBLE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new ChattingFragment()).commit();
                            setMyTitle("채팅");
                            mToolbar.setVisibility(View.VISIBLE);
                            return true;
                        case R.id.bottom_menu_myPage:
                            toolbarLogo.setVisibility(View.GONE);
                            toolbarTitle.setVisibility(View.VISIBLE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new MyPageFragment()).commit();
                            setMyTitle("마이페이지");
                            mToolbar.setVisibility(View.VISIBLE);
                            return true;
                    }
                    return false;
                }
            });
        } else {
            bottomNavi.setVisibility(View.GONE);
        }
    }

    // 타이틀 설정
    protected void setMyTitle(String title) {
        toolbarTitle.setText(title);
    }

    // 툴바 사용여부 (사용 기본)
    protected boolean useToolbar() {
        return useToolbar;
    }

    // BottomNavi 사용여부 (사용 기본)
    protected boolean useBottomNavi() {
        return useBottomNavi;
    }

    // 뒤로가기 버튼 사용여부 (사용 기본)
    protected boolean useBackButton() {
        return useBackButton;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_top, menu);

        return true;
    }



    // 앱바 메뉴 클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.menu_alarm:{
                Intent intent = new Intent(this, AlarmActivity.class);
                startActivity(intent);
            }
            case R.id.menu_search:{
                //검색 연결
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //사용자 푸시 토큰 생성해서 저장
    public void registerPushToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Failed to get token");
                            return;
                        }
                        String token = task.getResult();
                        Log.d("sys",token);
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Map<String, String> map = new HashMap<>();
                        map.put("pushToken", token);

                        FirebaseFirestore.getInstance().collection("userProfile").document(uid).set(map, SetOptions.merge());
                    }
                });
    }

//    public void onStop(){ //확인용
//        super.onStop();
//        sendGson(FirebaseAuth.getInstance().getUid(), "title","message");
//    }
    public Toolbar getmToolbar() {
        return mToolbar;
    }
}