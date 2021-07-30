package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;


public class BaseActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView toolbarTitle;
    BottomNavigationView bottomNavi;

    private Boolean useToolbar = true;
    private Boolean useBottomNavi = true;

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
        bottomNavi = findViewById(R.id.bottomNavi);;

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

        // BottomNavi 사용 여부
        if (useBottomNavi()) {
            // 첫 화면 설정
            setMyTitle("홈");
            getSupportFragmentManager().beginTransaction().add(R.id.activity_content, new HomeFragment()).commit();

            // BottomNavigationView 내부 아이템 설정
            bottomNavi.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
                switch (item.getItemId()) {
                    case R.id.bottom_menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new HomeFragment()).commit();
                        setMyTitle("홈");
                        break;
                    case R.id.bottom_menu_post:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new PostFragment()).commit();
                        setMyTitle("게시판");
                        break;
                    case R.id.bottom_menu_chatting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new ChattingFragment()).commit();
                        setMyTitle("채팅");
                        break;
                    case R.id.bottom_menu_myPage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, new MyPageFragment()).commit();
                        setMyTitle("마이페이지");
                        break;
                }
                return true;
            });
        }
    }

    // 툴바 사용여부 (사용 기본)
    protected boolean useToolbar() {
        return useToolbar;
    }

    // 툴바 사용여부 변경
    public void setUseToolbar(Boolean useToolbar) {
        this.useToolbar = useToolbar;
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

    // BottomNavi 사용여부 (사용 기본)
    protected boolean useBottomNavi() {
        return useBottomNavi;
    }

    // BottomNavi 사용여부 변경
    public void setUseBottomNavi(Boolean useBottomNavi) {
        this.useBottomNavi = useBottomNavi;
    }
}