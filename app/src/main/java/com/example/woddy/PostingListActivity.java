package com.example.woddy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.SQLiteManager;
import com.example.woddy.Entity.Posting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostingListActivity extends BaseActivity {
    RecyclerView recyclerView;
    TextView textView;

    FirestoreManager manager = new FirestoreManager();
    PostingListAdapter adapter;

    String user = "user1";

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_list);

        Intent intent = getIntent();
        int activity = intent.getIntExtra("Activity", 0);

        textView = findViewById(R.id.posting_list_textview);
        recyclerView = findViewById(R.id.posting_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), recyclerView.VERTICAL, false)); // 상하 스크롤

        adapter = new PostingListAdapter();
        recyclerView.setAdapter(adapter);

        switch (activity) {
            case 1:
                setMyTitle("내가 작성한 글");
                myPost();
                break;
            case 2:
                setMyTitle("내가 좋아요 누른 글");
                break;
            case 3:
                setMyTitle("내가 스크랩한 글");
                break;
            default:
                break;
        }

    }

    private void myPost() {
        manager.getPostWithWriter(user).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot result = task.getResult();
                            if (!result.isEmpty()) {
                                ArrayList<Posting> postings = new ArrayList<>();
                                ArrayList<String> path = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    postings.add(document.toObject(Posting.class));
                                    path.add(document.getReference().getPath());
                                }
                                adapter.setItem(postings, path);

                            } else {
                                textView.setText("내가 작성한 글이 없습니다.");
                                textView.setVisibility(View.VISIBLE);
                                Log.d(TAG, "nothing found.", task.getException());
                            }
                        } else {
                            Log.d(TAG, "finding my posting task failed. error: " , task.getException());
                        }
                    }
                });
    }

    private void myLikedPost() {
        SQLiteManager sqlManager = new SQLiteManager(this);
        sqlManager.getScrapPost();
    }

    private void myScrappedPost() {

    }
}