package com.example.woddy.Album;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.woddy.PostWriting.AddWritingsActivity;
import com.example.woddy.R;

public class AlbummBoardFragment extends Fragment {
    ImageView addPosting;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albumm_board, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.album_recycler_view);
        addPosting = view.findViewById(R.id.add_new_posting);

        new AlbumData(getContext()).getItems(recyclerView);

        addPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddWritingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

}