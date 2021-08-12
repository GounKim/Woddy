package com.example.woddy.Posting;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Posting;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShowImgPosting extends BaseActivity {

    FirestoreManager manager;

    private ViewPager2 imgpost_slider;
    private LinearLayout layoutIndicator;
    private TextView title, writer, time, content;
    private TextView tag1, tag2, tag3, tag4, tag5;
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
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_posting);
        setTitle("게시판이름");

        Intent intent = getIntent();
        String postingNumber = intent.getStringExtra("postingNumber");

        title = findViewById(R.id.show_img_posting_title);
        writer = findViewById(R.id.show_img_posting_writer);
        time = findViewById(R.id.show_img_posting_time);
        content = findViewById(R.id.show_img_posting_content);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3 = findViewById(R.id.tag3);
        tag4 = findViewById(R.id.tag4);
        tag5 = findViewById(R.id.tag5);

        liked = findViewById(R.id.show_img_posting_liked);
        likedCount = findViewById(R.id.show_img_posting_likedCount);
        scrap = findViewById(R.id.show_img_posting_scrap);
        scrapCount = findViewById(R.id.show_img_posting_scrapCount);

        //이미지 슬라이더
        layoutIndicator = findViewById(R.id.show_img_posting_layoutIndicators);
        imgpost_slider = findViewById(R.id.show_img_posting_slider);

        imgpost_slider.setOffscreenPageLimit(1);
        imgpost_slider.setAdapter(new ShowImgPostingAdapter(images));

        imgpost_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(images.length);

        manager = new FirestoreManager();
        manager.getPostWithNum(postingNumber).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            if (!document.isEmpty()) {
                                Posting posting = document.toObjects(Posting.class).get(0);

                                title.setText(posting.getTitle());
                                writer.setText(posting.getWriter());
                                time.setText(datestamp(posting.getPostedTime()));
                                content.setText(posting.getContent());
                                tag1.setText(posting.getTag());
                                likedCount.setText(posting.getNumberOfLiked() + "");
                                scrapCount.setText(posting.getNumberOfScraped() + "");
                            } else {
                                Log.d(TAG, "fail to find ", task.getException());
                            }
                        } else {
                            Log.d(TAG, "finding posting task failed. error: " , task.getException());
                        }
                    }
                });
    }

    private String datestamp(Date date) {    // 자정에 생성되는 타임스탬프 생성
        TimeZone timeZone;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
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