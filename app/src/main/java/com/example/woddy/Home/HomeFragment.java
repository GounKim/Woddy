package com.example.woddy.Home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.Entity.Posting;
import com.example.woddy.LoadingActivity;
import com.example.woddy.PostWriting.AddWritingsActivity;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    Button btnLogin;

    FirestoreManager manager = new FirestoreManager();

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
                Intent intent = new Intent(getContext(), LoadingActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤

        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);

        setHomeAdapter();


        return view;
    }

    void setHomeAdapter() {
        // 공지 Board
        manager.getPostWithTag("notice").limit(3).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Posting> notices = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                notices.add(document.toObject(Posting.class));
                            }

                            HomeNBAdapter nAdapter = new HomeNBAdapter(notices);
                            homeAdapter.addItem(nAdapter);

                            manager.getPopularPost().get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<Posting> popPosts = new ArrayList<>();
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    popPosts.add(document.toObject(Posting.class));
                                                }

                                                HomePBAdapter pbAdapter = new HomePBAdapter(popPosts);
                                                homeAdapter.addItem(pbAdapter);

                                                // 최신글 Board
                                                manager.getCurrentPost().get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                ArrayList<Posting> curPosts = new ArrayList<>();
                                                                for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                                                                    curPosts.add(snap.toObject(Posting.class));
                                                                }
                                                                HomePBAdapter rbAdapter = new HomePBAdapter(curPosts);
                                                                homeAdapter.addItem(rbAdapter);

                                                                // 즐겨찾기한 게시판 Board
                                                                BoardTag boardTag = new BoardTag("집 소개", "자유게시판");
                                                                HomeFBAdapter fbAdapter = new HomeFBAdapter();
                                                                fbAdapter.addItem(boardTag);
                                                                fbAdapter.addItem(boardTag);
                                                                fbAdapter.addItem(boardTag);
                                                                fbAdapter.addItem(boardTag);
                                                                homeAdapter.addItem(fbAdapter);
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