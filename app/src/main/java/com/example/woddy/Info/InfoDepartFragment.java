package com.example.woddy.Info;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woddy.Entity.Depart;
import com.example.woddy.R;

import java.util.ArrayList;

public class InfoDepartFragment extends Fragment {

    private Context context;
    private final String BOARD_NAME = "기관";

    RecyclerViewAdapter adapter;

    public InfoDepartFragment() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static InfoDepartFragment newInstance(String param1, String param2) {
//        InfoDepartFragment fragment = new InfoDepartFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_depart, container, false);
        context = container.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_dept);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(BOARD_NAME);
        recyclerView.setAdapter(adapter);

        new DepartData(context).getItems(recyclerView, BOARD_NAME);
        return view;
    }
}