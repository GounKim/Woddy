package com.example.woddy.Album;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Posting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AlbumData {

    ArrayList<Posting> items = new ArrayList<>();

    FirestoreManager manager = new FirestoreManager();
    FirebaseFirestore db;
    private AlbumAdapter adapter = new AlbumAdapter();

    public ArrayList<Posting> getItems(RecyclerView recyclerView) {

        //여기서 파이어스토어에서 정보 가져와서 items.add(positingNumber, 이미지주소, 게시글제목, 좋아요수);
//        db = FirebaseFirestore.getInstance();
//        CollectionReference productsRef = db.collection("postings");

//        Query query = productsRef.orderBy("postedTime", Query.Direction.DESCENDING);

        manager.getPostWithTag("태그3").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Posting posting = document.toObject(Posting.class);
                            items.add(posting);
                        }
                        //아이템 로드
                        adapter.setItems(items);

                        StaggeredGridLayoutManager staggeredGridLayoutManager
                                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(staggeredGridLayoutManager); // 상하 스크롤
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