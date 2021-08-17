package com.example.woddy.Home;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.woddy.Alarm.AlarmActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Login.LogInActivity;
import com.example.woddy.R;
import com.example.woddy.Search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    Button btnLogin;
    Button btnAlarm;
    Button btnshow;
    Button btnDelAccount;
    Button btnLogout;

    FirestoreManager manager = new FirestoreManager(getContext());
    HomePBAdapter popAdapter = new HomePBAdapter();
    HomePBAdapter reAdapter = new HomePBAdapter();

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
        //회원탈퇴
        btnDelAccount = view.findViewById(R.id.button4);
        btnDelAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = firebaseUser.getUid();
                Log.d("tag", uid);
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            Log.d(TAG, "User account delete completed.");
                            manager.deleteProfile(uid);

                            Toast.makeText(getContext(), "탈퇴가 완료되었습니다", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(), LogInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Log.d("tag", task.getException().toString());
                        }
                    }
                });
            }
        });
        btnLogout = view.findViewById(R.id.button5);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), LogInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnshow = view.findViewById(R.id.button3);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤

        homeAdapter = new HomeAdapter();
        recyclerView.setAdapter(homeAdapter);

            //setHomeAdapter();

        return view;
    }
/*
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

 */
}