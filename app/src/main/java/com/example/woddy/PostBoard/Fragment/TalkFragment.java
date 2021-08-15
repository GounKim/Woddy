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
import com.google.android.material.chip.ChipGroup;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TalkFragment extends Fragment {

    private RecyclerView mVerticalView;
    private PostBoardAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ChipGroup chipGroup;

    private ArrayList<Posting> postingList = new ArrayList<>();
    private ArrayList<String> postingPath = new ArrayList<>();
    private Context context;

    private final String BOARD_NAME = "소통";
    private String tagName;

    public TalkFragment() {
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
        View view = inflater.inflate(R.layout.post_board_fragment_talk, container, false);
        context = container.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Talk);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);

        //default 부분 - 시작 시
        chipGroup.getChildAt(0).callOnClick();

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipFriend:
                        tagName = "친구찾기";
                        new NormalData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);
                        break;

                    case R.id.chipHelp:
                        tagName = "도움요청";
                        new NormalData(getContext()).getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);
                        break;

                    case R.id.chipMate:
                        tagName = "퇴근메이트";
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