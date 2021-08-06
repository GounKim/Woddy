package com.example.woddy.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Entity.Posting;
import com.example.woddy.PostWriting.AddWritingsActivity;
import com.example.woddy.R;

import java.util.Date;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.home_recyclerView);

        homeAdapter = new HomeAdapter(getContext());
        // 공지 Board
        Posting notice = new Posting("notice","administrator","공지 제목","공지 내용 입니다.",new Date());
        HomeNBAdapter nAdapter = new HomeNBAdapter(new Posting[]{notice, notice, notice});
        homeAdapter.addItem(nAdapter);

        // 인기글 Board
        Posting pop1 = new Posting("free","user1","제목 작성","내용 작성", new Date());
        Posting pop2 = new Posting("free","user1","제목 작성","내용 작성",String.valueOf(R.drawable.sample_image2), new Date());
        HomePBAdapter pbAdapter = new HomePBAdapter(new Posting[]{pop1, pop2, pop1});
        homeAdapter.addItem(pbAdapter);

        // 최신글 Board
        HomePBAdapter rbAdapter = new HomePBAdapter(new Posting[]{pop2, pop1, pop2});
        homeAdapter.addItem(rbAdapter);

        // 즐겨찾기한 게시판 Board
        BoardTag boardTag = new BoardTag("집 소개", "자유게시판");
        HomeFBAdapter fbAdapter = new HomeFBAdapter();
        fbAdapter.addItem(boardTag);
        fbAdapter.addItem(boardTag);
        fbAdapter.addItem(boardTag);
        fbAdapter.addItem(boardTag);
        homeAdapter.addItem(fbAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(homeAdapter);


        return view;
    }
}