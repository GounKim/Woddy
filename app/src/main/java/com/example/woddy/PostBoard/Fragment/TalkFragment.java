package com.example.woddy.PostBoard.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.UserFavoriteBoard;
import com.example.woddy.PostBoard.NormalData;
import com.example.woddy.PostBoard.PostBoardAdapter;
import com.example.woddy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class TalkFragment extends Fragment {

    private FirestoreManager fsManager;
    private SwipeRefreshLayout swipeRefresh;

    ChipGroup chipGroup;
    Chip friend, help, mate;

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

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Talk);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);

        friend = (Chip) view.findViewById(R.id.chipFriend);
        help = (Chip) view.findViewById(R.id.chipHelp);
        mate = (Chip) view.findViewById(R.id.chipMate);

        //default 부분 - 시작 시
        tagName = "친구찾기";
        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
//        givePathToParent(tagName);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                givePathToParent(tagName);
                swipeRefresh.setRefreshing(false);
            }
        });

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chipFriend:
                        tagName = "친구찾기";
                        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);

                        break;

                    case R.id.chipHelp:
                        tagName = "도움요청";
                        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);

                        break;

                    case R.id.chipMate:
                        tagName = "퇴근메이트";
                        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                        givePathToParent(tagName);

                        break;
                }
            }
        });


        chipGroup.setLongClickable(true);
        chipGroup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (v.getId()) {
                    case R.id.chipFriend:
                        UserFavoriteBoard friendBoard = new UserFavoriteBoard("친구찾기");
                        fsManager.addUFavorBoard(friendBoard);
                        return true;

                    case R.id.chipHelp:
                        UserFavoriteBoard helpBoard = new UserFavoriteBoard("도움요청");
                        fsManager.addUFavorBoard(helpBoard);
                        return true;

                    case R.id.chipMate:
                        UserFavoriteBoard mateBoard = new UserFavoriteBoard("퇴근메이트");
                        fsManager.addUFavorBoard(mateBoard);
                        return true;

                }
                return true;
            }
        });

        return view;
    }
}