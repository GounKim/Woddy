package com.example.woddy.Posting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.News;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShowInfoPosting extends BaseActivity {
    FirestoreManager manager = new FirestoreManager();

    private ViewPager2 imgpost_slider;
    private LinearLayout layoutIndicator;
    private TextView title, writer, time, content, tag, board, writerUid;

    String postingPath, boardName, tagName;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_posting);

        Intent intent = getIntent();
        postingPath = intent.getStringExtra("documentPath");
        String[] path = postingPath.split("/");
        boardName = path[1];
        tagName = path[3];

        setMyTitle(boardName);

        title = findViewById(R.id.show_img_posting_title);
        writer = findViewById(R.id.show_img_posting_writer);
        time = findViewById(R.id.show_img_posting_time);
        content = findViewById(R.id.show_img_posting_content);
        tag = findViewById(R.id.show_img_posting_tag);
        board = findViewById(R.id.show_img_posting_boardName);

        //이미지 슬라이더
        layoutIndicator = findViewById(R.id.show_img_posting_layoutIndicators);
        imgpost_slider = findViewById(R.id.show_img_posting_slider);

        manager.getdocRefWithPath(postingPath).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                News news = document.toObject(News.class);

                                title.setText(news.getTitle());
                                writer.setText("관리자");
                                time.setText(datestamp(news.getPostedTime()));
                                content.setText(news.getContent());
                                board.setText(boardName);
                                tag.setText("#" + tagName);

                                String[] imageList = news.getPictures().toArray(new String[0]);
                                imgpost_slider.setOffscreenPageLimit(1);
                                imgpost_slider.setAdapter(new ShowImgPostingAdapter(imageList));

                                imgpost_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                    @Override
                                    public void onPageSelected(int position) {
                                        super.onPageSelected(position);
                                        setCurrentIndicator(position);
                                    }
                                });
                                setupIndicators(imageList.length);
                            }
                        }
                    }
                });
    }

    private String datestamp(Date date) {    // 자정에 생성되는 타임스탬프 생성
        TimeZone timeZone;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (E)", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
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
}