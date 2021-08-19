package com.example.woddy.Posting;

import static android.content.ContentValues.TAG;

//import static com.example.woddy.DB.FirestoreManager.USER_UID;


import static com.example.woddy.DB.FirestoreManager.USER_UID;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.SQLiteManager;
import com.example.woddy.DB.StorageManager;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.Entity.Comment;
import com.example.woddy.Entity.Posting;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class ShowImgPosting extends BaseActivity implements View.OnClickListener {
    SQLiteManager sqlManager = new SQLiteManager(this);
    FirestoreManager manager = new FirestoreManager();

    CommentAdapter commentAdapter;

    private ViewPager2 imgpost_slider;
    private LinearLayout layoutIndicator;
    private TextView title, writer, time, content, tag, board, writerUid;
    private ImageView liked;
    private TextView likedCount;
    private ImageView scrap;
    private TextView scrapCount;
    private EditText edtComment;
    private Button btnSend;
    private RecyclerView commentView;

    String postingPath, boardName, tagName;

    //좋아요, 스크랩 버튼을 위한 변수
    private int i = 1, y = 1;

    // BottomSheetDialog
    LinearLayout bottomLayout;
    TextView report, sendChat, cancle, delete;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_posting);

        Intent intent = getIntent();
        postingPath = intent.getStringExtra("documentPath");
        String[] path = postingPath.split("/");
        boardName = path[1];
        tagName = path[3];

        setTitle(boardName);

        title = findViewById(R.id.show_img_posting_title);
        writer = findViewById(R.id.show_img_posting_writer);
        writerUid = findViewById(R.id.show_img_posting_writerUid);
        time = findViewById(R.id.show_img_posting_time);
        content = findViewById(R.id.show_img_posting_content);
        tag = findViewById(R.id.show_img_posting_tag);
        board = findViewById(R.id.show_img_posting_boardName);

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

        commentAdapter = new CommentAdapter();
        commentView.setLayoutManager(new LinearLayoutManager(this, commentView.VERTICAL, false)); // 상하 스크롤
        commentView.setAdapter(commentAdapter);
        getComments(commentAdapter);

        btnSend.setOnClickListener(this);

        getComments(commentAdapter);

        manager.getdocRefWithPath(postingPath).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Posting posting = document.toObject(Posting.class);

                                title.setText(posting.getTitle());
                                writer.setText(posting.getWriter());
                                writerUid.setText(posting.getPostingUid());
                                time.setText(datestamp(posting.getPostedTime()));
                                content.setText(posting.getContent());
                                board.setText(boardName);
                                tag.setText("#" + tagName);
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
        Comment comment = new Comment(sqlManager.getUserNick(), text, "");
        commentAdapter.addItem(comment);
        manager.addComment(postingPath, comment);

        edtComment.setText(""); // 입력창 초기화
        // 키보드 내리기
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(edtComment.getWindowToken(), 0);
    }

    private void getComments(CommentAdapter adapter) {
        manager.getComments(postingPath).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty() && task.getResult() != null) {
                                ArrayList<Comment> commentList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    commentList.add(document.toObject(Comment.class));
                                }
                                adapter.setItem(commentList);
                            }
                        } else {
                            Log.d(TAG, "Finding comments failed.", task.getException());
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

    public void pushHeart(View view){
        i = i * (-1);
        int num = Integer.parseInt((String) likedCount.getText());
        if(i == -1) {
            liked.setImageResource(R.drawable.ic_baseline_liked_yes);
            likedCount.setText(Integer.toString(num + 1));
            manager.updatePostInfo(postingPath, FirestoreManager.LIKE, FirestoreManager.INCRESE);
            sqlManager.insertLiked(postingPath);
            FirebaseFirestore.getInstance().document(postingPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Posting posting = document.toObject(Posting.class);

                            String uid=posting.getPostingUid();
                            manager.likeAlarm(uid, postingPath);
                        }
                    }
                }
            });
        }else{
            liked.setImageResource(R.drawable.ic_baseline_liked_no);
            likedCount.setText(Integer.toString(num - 1));
            manager.updatePostInfo(postingPath, FirestoreManager.LIKE, FirestoreManager.DECRESE);
        }
    }

    public void pushClip(View view){
        y = y * (-1);
        int num = Integer.parseInt((String) scrapCount.getText());
        if(y == -1) {
            scrap.setImageResource(R.drawable.ic_baseline_scraped_yes);
            scrapCount.setText(Integer.toString(num+1));
            manager.updatePostInfo(postingPath, FirestoreManager.SCRAP, FirestoreManager.INCRESE);

            manager.getdocRefWithPath(postingPath).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Posting posting = documentSnapshot.toObject(Posting.class);
                                posting.setPostingNumber(postingPath);
                                sqlManager.insertPosting(boardName, tagName, posting);
                            } else {
                                Log.d(TAG, "There's no document");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "fail to find ", e);
                        }
                    });

        }else{
            scrap.setImageResource(R.drawable.ic_baseline_scraped_no);
            scrapCount.setText(Integer.toString(num-1));
            manager.updatePostInfo(postingPath, FirestoreManager.SCRAP, FirestoreManager.DECRESE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_posting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_more_option:

                View bottomSheetView = getLayoutInflater().inflate(R.layout.show_posting_menu, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(bottomSheetView);

                bottomLayout = bottomSheetDialog.findViewById(R.id.show_posting_menu_layout);
                delete = bottomSheetDialog.findViewById(R.id.show_posting_menu_delete);
                sendChat = bottomSheetDialog.findViewById(R.id.show_posting_menu_send_chatting);

                if (writerUid.getText().toString() == USER_UID) {
                    bottomLayout.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.VISIBLE);

                } else {
                    bottomLayout.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.INVISIBLE);

                    sendChat.setOnClickListener(this::bottomSheet);
                }


                bottomSheetDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void bottomSheet(View view) {
        switch (view.getId()) {

            case R.id.show_posting_menu_report:_posting_report:

            break;

            case R.id.show_posting_menu_send_chatting:
                String w = writer.getText().toString();
                manager.findUserWithNick(w)
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                String wNick = (String) document.get("nickname");
                                String wImage = (String) document.get("userImage");

                                String[] participant = {wNick, sqlManager.getUserNick()};
                                String[] chatterImage = {wImage, sqlManager.getUserImage()};

                                ChattingInfo chattingInfo = new ChattingInfo(Arrays.asList(participant), Arrays.asList(chatterImage));
                                manager.addChatRoom(chattingInfo);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Fail to find writer info");
                            }
                        });


                break;

            case R.id.show_posting_menu_delete:

                break;

            case R.id.show_posting_menu_cancle:


                break;
        }
    }
}