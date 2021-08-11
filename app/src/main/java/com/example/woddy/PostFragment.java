package com.example.woddy;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Posting.ShowPosting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PostFragment extends Fragment {
    ImageView addPosting;
//    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        addPosting = view.findViewById(R.id.add_new_posting);


        Intent intent = new Intent(getActivity(),NoticeMain.class);
        startActivity(intent);

//        textView = view.findViewById(R.id.testTextView);

//        FirestoreManager manager = new FirestoreManager();
//        manager.addBoard(new BoardTag("자유게시판", "인테리어 공유"));
//
//        FirebaseFirestore fsDB = FirebaseFirestore.getInstance();
//
//        fsDB.collectionGroup("postings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });

        addPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}