package com.example.woddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.woddy.Notice.NoticeMain;
import com.example.woddy.Posting.ShowPosting;

public class PostFragment extends Fragment {
    ImageView addPosting;
//    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        addPosting = view.findViewById(R.id.add_new_posting);


        Intent intent = new Intent(getActivity(), NoticeMain.class);
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