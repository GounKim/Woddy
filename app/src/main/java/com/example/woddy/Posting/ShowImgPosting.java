package com.example.woddy.Posting;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Comment;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShowImgPosting extends BaseActivity implements View.OnClickListener {

    FirestoreManager manager;
    CommentAdapter commentAdapter;

    private ViewPager2 imgpost_slider;
    private LinearLayout layoutIndicator;
    private TextView title, writer, time, content, tag;
    private ImageView liked;
    private TextView likedCount;
    private ImageView scrap;
    private TextView scrapCount;
    private EditText edtComment;
    private Button btnSend;
    private RecyclerView commentView;

    String postingNumber;
    String postingDocRef;

    //좋아요, 스크랩 버튼을 위한 변수
    private int i = 1, y = 1;

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
        postingNumber = intent.getStringExtra("postingNumber");
        postingDocRef = intent.getStringExtra("documentReference");

        title = findViewById(R.id.show_img_posting_title);
        writer = findViewById(R.id.show_img_posting_writer);
        time = findViewById(R.id.show_img_posting_time);
        content = findViewById(R.id.show_img_posting_content);
        tag = findViewById(R.id.show_img_posting_tag);

        liked = findViewById(R.id.show_img_posting_liked);
        likedCount = findViewById(R.id.show_img_posting_likedCount);
        scrap = findViewById(R.id.show_img_posting_scrap);
        scrapCount = findViewById(R.id.show_img_posting_scrapCount);

        //이미지 슬라이더
        layoutIndicator = findViewById(R.id.show_img_posting_layoutIndicators);
        imgpost_slider = findViewById(R.id.show_img_posting_slider);

        // 댓글
        commentView = findViewById(R.id.show_img_posting_commentView);
        edtComment = findViewById(R.id.show_img_posting_edt_comment);
        btnSend = findViewById(R.id.show_img_posting_btnSend_comment);

        btnSend.setOnClickListener(this);
        commentAdapter = new CommentAdapter();
        commentView.setAdapter(commentAdapter);

        manager = new FirestoreManager(getApplicationContext());
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
                                tag.setText("#" + posting.getTag());
                                likedCount.setText(posting.getNumberOfLiked() + "");
                                scrapCount.setText(posting.getNumberOfScraped() + "");

                                // 이미지 설정
                                String[] imageList = posting.getPictures().toArray(new String[0]);
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

                            } else {
                                Log.d(TAG, "fail to find ", task.getException());
                            }
                        } else {
                            Log.d(TAG, "finding posting task failed. error: " , task.getException());
                        }
                    }
                });
    }


    // 댓글 달기
    @Override
    public void onClick(View view) {
        String text = edtComment.getText().toString();
        Comment comment = new Comment("user1", text, "");
        commentAdapter.addItem(comment);
        manager.addComment(postingDocRef,comment);
    }

    private void getComments(CommentAdapter adapter) {
        manager.getComments(postingNumber).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Comment> commentList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                commentList.add(document.toObject(Comment.class));
                            }
                            adapter.setItem(commentList);

                        } else {
                            Log.d(TAG, "Finding comments failed.", task.getException());
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
            likedCount.setText(Integer.toString(num + 1));
            Toast.makeText(getApplicationContext(), postingNumber, Toast.LENGTH_SHORT).show();
            manager.updatePostInfo(postingNumber, FirestoreManager.LIKE, FirestoreManager.INCRESE);
        }else{
            liked.setImageResource(R.drawable.heart_off);
            likedCount.setText(Integer.toString(num - 1));
            manager.updatePostInfo(postingNumber, FirestoreManager.LIKE, FirestoreManager.DECRESE);
        }
    }

    public void pushClip(View view){
        y = y * (-1);
        int num = Integer.parseInt((String) scrapCount.getText());
        if(y == -1) {
            scrap.setImageResource(R.drawable.clip_on);
            scrapCount.setText(Integer.toString(num+1));
            manager.updatePostInfo(postingNumber, FirestoreManager.SCRAP, FirestoreManager.INCRESE);
        }else{
            scrap.setImageResource(R.drawable.clip_off);
            scrapCount.setText(Integer.toString(num-1));
            manager.updatePostInfo(postingNumber, FirestoreManager.SCRAP, FirestoreManager.DECRESE);
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