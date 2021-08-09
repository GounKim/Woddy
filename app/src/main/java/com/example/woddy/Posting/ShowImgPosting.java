package com.example.woddy.Posting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woddy.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowImgPosting extends AppCompatActivity {

    FirebaseFirestore db;

    private ViewPager2 imgpost_slider;
    private LinearLayout layoutIndicator;

    Toolbar imgpost_toolbar;

    private TextView title;
    private TextView writer;
    private TextView time;
    private TextView content;
    private TextView tag1;
    private TextView tag2;
    private TextView tag3;
    private TextView tag4;
    private TextView tag5;

    private ImageView liked;
    private TextView likedCount;
    private ImageView scrap;
    private TextView scrapCount;

    //좋아요, 스크랩 버튼을 위한 변수
    private int i = 1, y = 1;

    //db에서 이미지 가져오기
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
        String postingNumber = intent.getStringExtra("postingNumber");

        imgpost_toolbar = findViewById(R.id.imgpost_toolbar);
        setSupportActionBar(imgpost_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 백버튼 추가
        getSupportActionBar().setTitle("");

        title = findViewById(R.id.title);
        writer = findViewById(R.id.writer);
        time = findViewById(R.id.time);
        content = findViewById(R.id.content);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3 = findViewById(R.id.tag3);
        tag4 = findViewById(R.id.tag4);
        tag5 = findViewById(R.id.tag5);

        liked = findViewById(R.id.liked);
        likedCount = findViewById(R.id.likedCount);
        scrap = findViewById(R.id.scrap);
        scrapCount = findViewById(R.id.scrapCount);

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

        //db에서 데이터 가져와서 집어넣기
//        db = FirebaseFirestore.getInstance();
//
//        DocumentReference docRef = db.collection("postings").document(postingNumber);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        title.setText(document.getData().get("postingNumber").toString());
//                        writer.setText(document.getData().get("writer").toString());
//                        time.setText(document.getData().get("postedTime").toString());
//                        content.setText(document.getData().get("content").toString());
//                        tag1.setText(document.getData().get("tag").toString());
//                        likedCount.setText(document.getData().get("numberOfLiekd").toString());
//                        scrapCount.setText(document.getData().get("numberOfScraped").toString());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

    }

    public void pushHeart(View view){
        i = i * (-1);
        int num = Integer.parseInt((String) likedCount.getText());
        if(i == -1) {
            liked.setImageResource(R.drawable.heart_on);
            likedCount.setText(Integer.toString(num+1));
        }else{
            liked.setImageResource(R.drawable.heart_off);
            likedCount.setText(Integer.toString(num-1));
        }
    }

    public void pushClip(View view){
        y = y * (-1);
        int num = Integer.parseInt((String) scrapCount.getText());
        if(y == -1) {
            scrap.setImageResource(R.drawable.clip_on);
            scrapCount.setText(Integer.toString(num+1));
        }else{
            scrap.setImageResource(R.drawable.clip_off);
            scrapCount.setText(Integer.toString(num-1));
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