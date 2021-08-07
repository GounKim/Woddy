package com.example.woddy.Home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.woddy.PostWriting.AddWritingsActivity;
import com.example.woddy.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;

    FirestoreManager manager = new FirestoreManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤

        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);

        setHomeAdapter();


        return view;
    }

    void setHomeAdapter() {
        // 공지 Board
        Posting notice = new Posting("notice","administrator","공지 제목","공지 내용 입니다.",new Date());
        HomeNBAdapter nAdapter = new HomeNBAdapter(new Posting[]{notice, notice, notice});
        homeAdapter.addItem(nAdapter);



        // 인기글 Board
        manager.getPopularPost().limit(3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Finding PopularPost failed.", error);
                    return;
                }

                ArrayList<Posting> popPosts = new ArrayList<>();
                for (DocumentSnapshot doc: value.getDocuments()) {
                    popPosts.add(doc.toObject(Posting.class));
                }
                HomePBAdapter pbAdapter = new HomePBAdapter(popPosts);
                homeAdapter.addItem(pbAdapter);

                // 최신글 Board
                manager.getCurrentPost().limit(3).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Finding PopularPost failed.", error);
                            return;
                        }

                        ArrayList<Posting> curPosts = new ArrayList<>();
                        for (DocumentSnapshot doc: value.getDocuments()) {
                            curPosts.add(doc.toObject(Posting.class));
                        }
                        HomePBAdapter pbAdapter = new HomePBAdapter(curPosts);
                        homeAdapter.addItem(pbAdapter);

                        // 즐겨찾기한 게시판 Board
                        BoardTag boardTag = new BoardTag("집 소개", "자유게시판");
                        HomeFBAdapter fbAdapter = new HomeFBAdapter();
                        fbAdapter.addItem(boardTag);
                        fbAdapter.addItem(boardTag);
                        fbAdapter.addItem(boardTag);
                        fbAdapter.addItem(boardTag);
                        homeAdapter.addItem(fbAdapter);
                    }
                });
            }
        });
    }
}