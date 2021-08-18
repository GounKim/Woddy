package com.example.woddy.Posting;

import static android.content.ContentValues.TAG;

import static com.example.woddy.DB.FirestoreManager.USER_UID;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.SQLiteManager;
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

public class ShowPosting extends BaseActivity implements View.OnClickListener {
    FirestoreManager manager = new FirestoreManager();
    SQLiteManager sqlManager = new SQLiteManager(this);

    TextView title, writer, time, content, likedCount, scrapCount, tag, board;
    ImageView liked, scrap;
    RecyclerView recyclerView;
    EditText edtComment;
    Button btnSend;

    CommentAdapter adapter;

    String postingPath;
    String boardName;
    String tagName;

    //좋아요, 스크랩 버튼을 위한 변수
    private int i = 1, y = 1;

    // BottomSheetDialog
    TextView report, sendChat, cancle;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posting);

        Intent intent = getIntent();
        postingPath = intent.getStringExtra("documentPath");
        String[] path = postingPath.split("/");
        boardName = path[1];
        tagName = path[3];

        setTitle(boardName);

        title = findViewById(R.id.show_posting_title);
        writer = findViewById(R.id.show_posting_writer);
        time = findViewById(R.id.show_posting_time);
        content = findViewById(R.id.show_posting_content);
        board = findViewById(R.id.show_posting_boardName);
        tag = findViewById(R.id.show_posting_tag);

        liked = findViewById(R.id.show_posting_liked_image);
        likedCount = findViewById(R.id.show_posting_likedCount);
        scrap = findViewById(R.id.show_posting_scraped_image);
        scrapCount = findViewById(R.id.show_posting_scrapCount);

        recyclerView = findViewById(R.id.show_posting_comment_view);
        edtComment = findViewById(R.id.show_posting_edt_comment);
        btnSend = findViewById(R.id.show_posting_btnSend_comment);

        adapter = new CommentAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(adapter);
        getComments();

        btnSend.setOnClickListener(this);

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
                                time.setText(datestamp(posting.getPostedTime()));
                                content.setText(posting.getContent());
                                board.setText(boardName);
                                tag.setText("#" + tagName);
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

    @Override
    public void onClick(View view) {
        String text = edtComment.getText().toString();
        Comment comment = new Comment(sqlManager.getUserNick(), text, "");
        adapter.addItem(comment);
        manager.addComment(postingPath, comment);

        edtComment.setText(""); // 입력창 초기화
        // 키보드 내리기
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(edtComment.getWindowToken(), 0);
    }

    private void getComments() {
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
                            } else {
                                Log.d(TAG, "no comments in posting.");
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

                sendChat = bottomSheetDialog.findViewById(R.id.show_posting_send_chatting);
                sendChat.setOnClickListener(this::bottomSheet);

                bottomSheetDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void bottomSheet(View view) {
        switch (view.getId()) {
            case R.id.show_posting_report:

                break;

            case R.id.show_posting_send_chatting:
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

            case R.id.show_posting_cancle:

                break;
        }
    }
}