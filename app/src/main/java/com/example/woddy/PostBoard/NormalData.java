package com.example.woddy.PostBoard;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Posting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NormalData {

    ArrayList<Posting> items = new ArrayList<>();
    ArrayList<String> docPath = new ArrayList<String>();

    FirestoreManager manager;
    private PostBoardAdapter adapter;

    public NormalData(Context context) {
        this.manager = new FirestoreManager(context);
    }

    public ArrayList<Posting> getItems(RecyclerView recyclerView, String boardName, String tagName) {
        adapter = new PostBoardAdapter(boardName, tagName);

        manager.getPost(boardName, tagName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Posting posting = document.toObject(Posting.class);
                            docPath.add(document.getReference().getPath());
                            items.add(posting);
                        }
                        //아이템 로드
                        adapter.setItems(items, docPath);

                        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "Nothing Founded!");
                    }

                } else {
                    Log.d(TAG, "Finding Postings failed!");
                }
            }
        });


        return items;
    }

}