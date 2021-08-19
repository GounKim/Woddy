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

public class SearchInfoData {
    ArrayList<News> items = new ArrayList<>();
    ArrayList<String> docPath = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirestoreManager manager;
    private InfoBoardAdapter adapter;

    public SearchInfoData() {
        this.manager = new FirestoreManager();
    }

    public ArrayList<News> getItems(RecyclerView recyclerView, String boardName, String tagName,String searchWord) {
        adapter = new InfoBoardAdapter(boardName, tagName);

        final CollectionReference docRef =
                db.collection("postBoard").document(boardName).collection("postTag").document(tagName).collection("postings");

        docRef.whereGreaterThanOrEqualTo("content", searchWord.toLowerCase()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG,"message => " + searchWord.toLowerCase());
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
        return items;
    }
}