package com.example.woddy.Home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Login.LogInActivity;
import com.example.woddy.NoticeMain;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    Button btnLogin;
    Button btnshow;

    FirestoreManager manager = new FirestoreManager();
    HomePBAdapter popAdapter = new HomePBAdapter();
    HomePBAdapter reAdapter = new HomePBAdapter();

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//
//        ArrayList<Posting> popList = popAdapter.getItem();
//        ArrayList<Posting> recentList = reAdapter.getItem();
//        outState.putParcelableArrayList("popularPosting", popList);
//        outState.putParcelableArrayList("recentPosting", recentList);
//
//        Toast.makeText(getContext(), "저장완료", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // test용(로그인화면)
        btnLogin = view.findViewById(R.id.button2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LogInActivity.class);
                startActivity(intent);
            }
        });
        btnshow = view.findViewById(R.id.button3);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoticeMain.class);
                startActivity(intent);
            }
        });


        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤

//        if(savedInstanceState != null) {
//            Toast.makeText(getContext(), "======i=========", Toast.LENGTH_SHORT).show();
//        } else {
            homeAdapter = new HomeAdapter();
            recyclerView.setAdapter(homeAdapter);

            setHomeAdapter();
//        }

        return view;
    }

    private void setHomeAdapter() {
        // 공지 Board
        manager.getPostWithTag("notice").limit(3).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Object> adapter = new ArrayList<>();
                            ArrayList<Posting> notices = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                notices.add(document.toObject(Posting.class));
                            }
                            adapter.add(new HomeNBAdapter(notices));

                            manager.getPopularPost().get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<Posting> popPosts = new ArrayList<>();
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    popPosts.add(document.toObject(Posting.class));
                                                }
                                                popAdapter.setItem(popPosts);
                                                adapter.add(popAdapter);

                                                // 최신글 Board
                                                manager.getCurrentPost().get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                ArrayList<Posting> recentPosts = new ArrayList<>();
                                                                for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                                                                    recentPosts.add(snap.toObject(Posting.class));
                                                                }
                                                                reAdapter.setItem(recentPosts);
                                                                adapter.add(reAdapter);

                                                                // 즐겨찾기한 게시판 Board
                                                                BoardTag boardTag = new BoardTag("집 소개", "자유게시판");
                                                                HomeFBAdapter fbAdapter = new HomeFBAdapter();
                                                                fbAdapter.addItem(boardTag);
                                                                fbAdapter.addItem(boardTag);
                                                                fbAdapter.addItem(boardTag);
                                                                fbAdapter.addItem(boardTag);
                                                                adapter.add(fbAdapter);

                                                                homeAdapter.setItem(adapter);
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Finding RecentPost failed.", e);
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "Finding PopularPost failed.", task.getException());
                                            }
                                        }
                                    });

                        } else {
                            Log.d(TAG, "Finding notice failed.", task.getException());
                        }
                    }
                });
    }
}