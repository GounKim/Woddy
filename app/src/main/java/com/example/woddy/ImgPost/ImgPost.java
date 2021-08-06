package com.example.woddy.ImgPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woddy.R;

public class ImgPost extends AppCompatActivity {

    private ViewPager2 imgpost_slider;
    private LinearLayout layoutIndicator;

    Toolbar imgpost_toolbar;

    private ImageView heart;
    private TextView heartCount;
    private ImageView clip;
    private TextView clipCount;

    private int i = 1, y = 1;

    private String[] images = new String[] {
            "https://i.ibb.co/gPTbSfG/sample-image.jpg",
            "https://i.ibb.co/gjh1fNR/sample-image2.jpg",
            "https://i.ibb.co/yg93jD9/sample-image4.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_post);

        Intent intent = getIntent();
        String postNumber = intent.getStringExtra("postingNumber");

        imgpost_toolbar = findViewById(R.id.imgpost_toolbar);
        setSupportActionBar(imgpost_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 백버튼 추가
        getSupportActionBar().setTitle("");

        heart = findViewById(R.id.heart);
        heartCount = findViewById(R.id.heart_count);
        clip = findViewById(R.id.clip);
        clipCount = findViewById(R.id.clip_count);

        //이미지 슬라이더
        layoutIndicator = findViewById(R.id.layoutIndicators);
        imgpost_slider = findViewById(R.id.imgpost_slider);

        imgpost_slider.setOffscreenPageLimit(1);
        imgpost_slider.setAdapter(new ImageSliderAdapter(this, images));

        imgpost_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(images.length);
    }

    public void pushHeart(View view){
        i = i * (-1);
        int num = Integer.parseInt((String) heartCount.getText());
        if(i == -1) {
            heart.setImageResource(R.drawable.heart_on);
            heartCount.setText(Integer.toString(num+1));
        }else{
            heart.setImageResource(R.drawable.heart_off);
            heartCount.setText(Integer.toString(num-1));
        }
    }

    public void pushClip(View view){
        y = y * (-1);
        int num = Integer.parseInt((String) clipCount.getText());
        if(y == -1) {
            clip.setImageResource(R.drawable.clip_on);
            clipCount.setText(Integer.toString(num+1));
        }else{
            clip.setImageResource(R.drawable.clip_off);
            clipCount.setText(Integer.toString(num-1));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }
}