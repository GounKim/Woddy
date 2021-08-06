package com.example.woddy.Album;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AlbumData {

    ArrayList<AlbumItem> items = new ArrayList<>();

    FirebaseFirestore db;

    public ArrayList<AlbumItem> getItems() {

//        // 파이어스토어 인스턴스 초기화
//        db = FirebaseFirestore.getInstance();
//        CollectionReference productRef = db.collection("posting");
//        db.collection("posting")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot document : task.getResult()){
//                                items.add(document.toObject(AlbumItem.class));
//                            }
//                            Log.d("sys", "onComplete: 데이터 반영 완료"+items);
//                            //recyclerAdapter.addFaqList(faqList);
//                        }else{
//                            Log.d("sys", "onComplete: 실패");
//                        }
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("sys", "onFailure: "+e.getMessage());
//            }
//        });

        AlbumItem movie1 = new AlbumItem("https://i.ibb.co/gPTbSfG/sample-image.jpg",
                 "우리 집 강아지는 복슬 강아지",100);

        AlbumItem movie2 = new AlbumItem("https://i.ibb.co/gjh1fNR/sample-image2.jpg",
                "아무거나 아무거나 아무거나",21);

        AlbumItem movie3 = new AlbumItem("https://i.ibb.co/yg93jD9/sample-image4.jpg",
                "블라블라",1039);

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);

        return items;
    }
}
