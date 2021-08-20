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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Login.LogInActivity;
import com.example.woddy.PostBoard.PostBoardMain;
import com.example.woddy.R;
import com.example.woddy.Search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static final String USER_UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    RecyclerView recyclerView;
    HomeAdapter homeAdapter;

    FirestoreManager manager = new FirestoreManager();
    HomePBAdapter popAdapter = new HomePBAdapter();
    HomePBAdapter reAdapter = new HomePBAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setHomeAdapter();

        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤

        homeAdapter = new HomeAdapter();
        recyclerView.setAdapter(homeAdapter);




        return view;
    }

    private void setHomeAdapter() {

        ArrayList<HomePBAdapter> adapterList = new ArrayList<>();
        // 공지 Board
        manager.getPopularPost().get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Posting> popPosts = new ArrayList<>();
                            ArrayList<String> popDocPath = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "공지글");
                                popPosts.add(document.toObject(Posting.class));
                                popDocPath.add(document.getReference().getPath());
                            }
                            popAdapter.setItem(popPosts, popDocPath);
//                            adapterList.add(popAdapter);
                        } else {
                            Log.d(TAG, "Finding PopularPost failed.", task.getException());
                        }
                    }
                });
        adapterList.add(new HomePBAdapter());

        // 인기글
        manager.getPopularPost().get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Posting> popPosts = new ArrayList<>();
                            ArrayList<String> popDocPath = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(TAG, "인기글");
                                popPosts.add(document.toObject(Posting.class));
                                popDocPath.add(document.getReference().getPath());
                            }
                            popAdapter.setItem(popPosts, popDocPath);
                            adapterList.add(popAdapter);
                        } else {
                            Log.d(TAG, "Finding PopularPost failed.", task.getException());
                        }
                    }
                });

        // 최신글 Board
        manager.getCurrentPost().get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            ArrayList<Posting> recentPosts = new ArrayList<>();
                            ArrayList<String> recDocPath = new ArrayList<>();
                            for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                                //if (snap.get("writer") != null) {
                                    Log.d(TAG, "최신글 => " + snap.getData());
                                    recentPosts.add(snap.toObject(Posting.class));
                                    recDocPath.add(snap.getReference().getPath());
                              //  }
                            }
                            reAdapter.setItem(recentPosts, recDocPath);
                            adapterList.add(reAdapter);

                            homeAdapter.setItem(adapterList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Finding RecentPost failed.", e);
            }
        });


    }

}