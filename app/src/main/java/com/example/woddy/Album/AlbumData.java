package com.example.woddy.Album;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

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

    ArrayList<AlbumItem> items = new ArrayList<>();

    FirebaseFirestore db;

    public ArrayList<AlbumItem> getItems() {

        //여기서 파이어스토어에서 정보 가져와서 items.add(positingNumber, 이미지주소, 게시글제목, 좋아요수);
//        db = FirebaseFirestore.getInstance();
//        CollectionReference productsRef = db.collection("postings");
//
//        Query query = productsRef.orderBy("postedTime", Query.Direction.DESCENDING);
//
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) { if (task.getResult().size() <= 0) {
//                    // 표시할 데이터가 없음 나가기
//                    } else {
//
//                    for (DocumentSnapshot document : task.getResult()) {
//                        items.add(new AlbumItem(
//                                document.getData().get("postingNumber").toString(),
//                                document.getData().get("pictures").toString(),
//                                document.getData().get("title").toString(),
//                                document.getData().get("numberOfLiked").toString()));
//
//                        Log.d("sys",document.getData().get("postingNumber").toString());
//                    }
//                    //어댭터setadapter
//                }
//                }
//            }
//        });

        AlbumItem movie1 = new AlbumItem("1", "https://i.ibb.co/gPTbSfG/sample-image.jpg",
                 "우리 집 강아지는 복슬 강아지 학교 갔다 다녀오면 멍멍멍","100");

        AlbumItem movie2 = new AlbumItem("2","https://i.ibb.co/gjh1fNR/sample-image2.jpg",
                "아무거나 아무거나 아무거나","21");

        AlbumItem movie3 = new AlbumItem("3", "https://i.ibb.co/yg93jD9/sample-image4.jpg",
                "블라블라","1039");

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