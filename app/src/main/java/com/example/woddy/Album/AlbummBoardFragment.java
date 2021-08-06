package com.example.woddy.Album;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woddy.Home.HomeAdapter;
import com.example.woddy.Home.HomeBoardAdapter;
import com.example.woddy.Home.HomePostings;
import com.example.woddy.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class AlbummBoardFragment extends Fragment {

    private AlbumAdapter adapter = new AlbumAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albumm_board, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.album_recycler_view);

        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager); // 상하 스크롤
        recyclerView.setAdapter(adapter);

        //아이템 로드
        adapter.setItems(new AlbumData().getItems());

        return view;
    }

}