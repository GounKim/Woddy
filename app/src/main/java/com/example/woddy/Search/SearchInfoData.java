package com.example.woddy.Search;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.News;
import com.example.woddy.Info.InfoBoardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SearchInfoData { //정보형일 경우
    ArrayList<News> items = new ArrayList<>(); // 포스팅
    ArrayList<String> docPath = new ArrayList<>(); //포스팅 위치
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // 데이터베이스

    FirestoreManager manager;
    private InfoBoardAdapter adapter;

    public SearchInfoData() {
        this.manager = new FirestoreManager();
    }

    public ArrayList<News> getItems(RecyclerView recyclerView, String boardName, String tagName, String searchWord) {
        adapter = new InfoBoardAdapter(boardName, tagName);
        // 받은 게시판과 태그이름을 바탕으로 데베 찾기
        final CollectionReference docRef =
                db.collection("postBoard").document(boardName).collection("postTag").document(tagName).collection("postings");
        // 받은 검색 내용과 같거나 많은 내용을 담은 포스팅 뽑기
        docRef.whereGreaterThanOrEqualTo("content", searchWord.toLowerCase()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "message => " + searchWord.toLowerCase());
                                    News news = document.toObject(News.class);
                                    docPath.add(document.getReference().getPath());
                                    items.add(news);
                                }
                                adapter.setItems(items, docPath); //아이템 로드

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
        return items;
    }
}