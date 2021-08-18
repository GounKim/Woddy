package com.example.woddy.Search;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Posting;
import com.example.woddy.PostBoard.PostBoardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchData {

    ArrayList<Posting> items = new ArrayList<>();
    ArrayList<String> docPath = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirestoreManager manager;
    private PostBoardAdapter adapter;

    public SearchData(Context context) {
        this.manager = new FirestoreManager();
    }

    public ArrayList<Posting> getItems(RecyclerView recyclerView, String boardName, String tagName, String searchWord) {

        adapter = new PostBoardAdapter(boardName, tagName);

        final CollectionReference docRef =
                db.collection("postBoard").document(boardName).collection("postTag").document(tagName).collection("postings");

        docRef.whereGreaterThanOrEqualTo("content",searchWord.toLowerCase()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //작업이 성공적으로 마쳤을때
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