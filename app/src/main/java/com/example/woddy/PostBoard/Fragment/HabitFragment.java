package com.example.woddy.PostBoard.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woddy.Entity.Posting;
import com.example.woddy.PostBoard.NormalData;
import com.example.woddy.PostBoard.PostBoardAdapter;
import com.example.woddy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HabitFragment extends Fragment {

    private RecyclerView mVerticalView;
    private PostBoardAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ChipGroup chipGroup;
    Chip club, meeting;

    private ArrayList<Posting> postingList = new ArrayList<>();
    private ArrayList<String> postingPath = new ArrayList<>();
    private Context context;

    private final String BOARD_NAME = "취미";
    private String tagName;

    public HabitFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.post_board_fragment_habit, container, false);
        context = container.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_habit);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);
        meeting = (Chip) view.findViewById(R.id.chipMeeting);
        club = (Chip) view.findViewById(R.id.chipClub);


        //default 부분 - 시작 시
        tagName = "동호회";
        new NormalData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
//        givePathToParent(tagName);

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipClub:
                        tagName = "동호회";
                        new NormalData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);

                        break;

                    case R.id.chipMeeting:
                        tagName = "번개모임";
                        new NormalData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);

                        break;
                }
            }
        });

        return view;
    }

    private void initDataset() { //들어갈 데이터
        //초기화

    }

}
