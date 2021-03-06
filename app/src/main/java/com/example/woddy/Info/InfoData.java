package com.example.woddy.Info;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InfoData {
    ArrayList<News> items = new ArrayList<>();
    ArrayList<String> docPath = new ArrayList<>();

    FirestoreManager manager;
    private InfoBoardAdapter adapter;

    String userSi;

    public InfoData() {
        this.manager = new FirestoreManager();
    }

    public ArrayList<News> getItems(RecyclerView recyclerView, String boardName, String tagName) {
        adapter = new InfoBoardAdapter(boardName, tagName);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //현재 로그인한 사용자의 uid로 사용자의 거주 지역(시 또는 도) get
        manager.findUserSiWithUid(uid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                userSi = documentSnapshot.getString("city");
                Log.d("InfoData", userSi);
                //사용자의 지역에 해당되는 정보를 가져옴
                manager.getNewsQueryBeta(boardName, tagName, userSi).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() > 0) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            News news = document.toObject(News.class);
                                            docPath.add(document.getReference().getPath());
                                            items.add(news);
                                        }
                                        adapter.setItems(items, docPath);

                                        StaggeredGridLayoutManager staggeredGridLayoutManager
                                                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                        recyclerView.setLayoutManager(staggeredGridLayoutManager); // 상하 스크롤
                                        recyclerView.setAdapter(adapter);
                                    } else {
                                        Log.d("InfoData", "Nothing Found");
                                    }
                                } else {
                                    Log.d("InfoData", "Finding news failed");
                                }
                            }
                        });
            }
        });

        return items;
    }
}