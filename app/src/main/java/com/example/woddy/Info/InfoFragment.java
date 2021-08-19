package com.example.woddy.Info;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.woddy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class InfoFragment extends Fragment {

    ChipGroup chipGroup;
    Chip lifeSupport, edu, event;

    private Context context;
    private final String BOARD_NAME = "정보";
    private String tagName;

    public InfoFragment() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static InfoFragment newInstance(String param1, String param2) {
//        InfoFragment fragment = new InfoFragment();
//        Bundle args = new Bundle();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void givePathToParent(String tagName) {
        Bundle result = new Bundle();
        result.putString("tagName", tagName);
        getParentFragmentManager().setFragmentResult("tagKey", result);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        context = container.getContext();

        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_info);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.InfoFilterChipGroup);
        lifeSupport = (Chip) view.findViewById(R.id.chipLifeSupport);
        edu = (Chip) view.findViewById(R.id.chipEducation);
        event = (Chip) view.findViewById(R.id.chipEvent);

        tagName = "생활지원";
        new InfoData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new InfoData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                givePathToParent(tagName);
                swipeRefresh.setRefreshing(false);
            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chipLifeSupport:
                        tagName = "생활지원";
                        new InfoData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);
                        break;
                    case R.id.chipEducation:
                        tagName = "교육";
                        new InfoData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);
                        break;
                    case R.id.chipEvent:
                        tagName = "행사";
                        new InfoData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);
                        break;
                }
            }
        });

        return view;
    }
}