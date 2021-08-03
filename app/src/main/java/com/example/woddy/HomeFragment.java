package com.example.woddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.InitDBdata;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeBoardAdapter myAdapter;
    HomeAdapter homeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_recyclerView);

        myAdapter = new HomeBoardAdapter();
        myAdapter.addItem(new HomePostings("writer1", "content1", "board1", 100, "18:30"));
        myAdapter.addItem(new HomePostings("writer2", "content2", "board2", 123, "8:30", String.valueOf(R.drawable.sample_image2)));
        myAdapter.addItem(new HomePostings("writer3", "content3", "board1", 10, "5:51"));
        myAdapter.addItem(new HomePostings("writer4", "content4", "board3", 140, "13:13", String.valueOf(R.drawable.sample_image4)));
        myAdapter.addItem(new HomePostings("writer5", "content5", "board2", 301, "20:02"));
        homeAdapter = new HomeAdapter();
        homeAdapter.addItem("알려드려요", myAdapter);
//        HomeBoardAdapter a = new HomeBoardAdapter();
//        a.addItem(new HomePostings("writer3", "content3", "board1", 10, "5:51"));
//        a.addItem(new HomePostings("writer4", "content4", "board3", 140, "13:13", String.valueOf(R.drawable.sample_image4)));
//        homeAdapter.addItem("현재 인기글", a);
        homeAdapter.addItem("최신글", myAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(homeAdapter);

        return view;
    }
}