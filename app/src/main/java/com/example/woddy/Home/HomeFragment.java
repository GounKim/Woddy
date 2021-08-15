package com.example.woddy.Home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woddy.Alarm.AlarmActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Login.LogInActivity;
import com.example.woddy.Notice.NoticeMain;
import com.example.woddy.PostWriting.AddWritingsActivity;
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
    Button btnalarm;

    FirestoreManager manager = new FirestoreManager(getContext());
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
                Intent intent = new Intent(getContext(), AddWritingsActivity.class);
                startActivity(intent);
            }
        });
        btnalarm = view.findViewById(R.id.button4);
        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmActivity.class);
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
                            ArrayList<Object> adapterList = new ArrayList<>();
                            ArrayList<Posting> notices = new ArrayList<>();
                            ArrayList<String> noticeDocPath = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                notices.add(document.toObject(Posting.class));
                            }
                            adapterList.add(new HomeNBAdapter(notices));

                            manager.getPopularPost().get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<Posting> popPosts = new ArrayList<>();
                                                ArrayList<String> popDocPath = new ArrayList<>();
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    popPosts.add(document.toObject(Posting.class));
                                                    popDocPath.add(document.getReference().getPath());
                                                }
                                                popAdapter.setItem(popPosts, popDocPath);
                                                adapterList.add(popAdapter);

                                                // 최신글 Board
                                                manager.getCurrentPost().get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                ArrayList<Posting> recentPosts = new ArrayList<>();
                                                                ArrayList<String> recDocPath = new ArrayList<>();
                                                                for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                                                                    recentPosts.add(snap.toObject(Posting.class));
                                                                    recDocPath.add(snap.getReference().getPath());
                                                                }
                                                                reAdapter.setItem(recentPosts, recDocPath);
                                                                adapterList.add(reAdapter);


                                                                homeAdapter.setItem(adapterList);
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