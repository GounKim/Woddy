package com.example.woddy.PostBoard.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.woddy.Entity.Posting;
import com.example.woddy.PostBoard.Album.AlbumData;
import com.example.woddy.PostBoard.NormalData;
import com.example.woddy.PostBoard.PostBoardAdapter;
import com.example.woddy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class FreeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView mVerticalView;
    private PostBoardAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ChipGroup chipGroup;
    Chip free, diy, interior, townInfo;

    private ArrayList<Posting> postingList = new ArrayList<>();
    private ArrayList<String> postingPath = new ArrayList<>();
    private Context context;

    private final String BOARD_NAME = "자유";
    private String tagName;

    public FreeFragment() {
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
        View view = inflater.inflate(R.layout.post_board_fragment_free, container, false);
        context = container.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_free);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);

        free = (Chip) view.findViewById(R.id.chipFreeTalk);
        diy = (Chip) view.findViewById(R.id.chipDIY);
        interior = (Chip) view.findViewById(R.id.chipInterior);
        townInfo = (Chip) view.findViewById(R.id.chipTownInfo);

        //default 부분 - 시작 시
        tagName = "자유";
        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                swipeRefresh.setRefreshing(false);
            }
        });
        givePathToParent(tagName);

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipFreeTalk:
                        tagName = "자유";
                        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);

                        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                        givePathToParent(tagName);

                        break;

                    case R.id.chipDIY:
                        tagName = "DIY";
                        new AlbumData().getItems(recyclerView, BOARD_NAME, tagName);

                        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                new AlbumData().getItems(recyclerView, BOARD_NAME, tagName);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                        givePathToParent(tagName);

                        break;

                    case R.id.chipInterior:
                        tagName = "인테리어";
                        new AlbumData().getItems(recyclerView, BOARD_NAME, tagName);

                        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                new AlbumData().getItems(recyclerView, BOARD_NAME, tagName);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                        givePathToParent(tagName);

                        break;

                    case R.id.chipTownInfo:
                        tagName = "동네정보";
                        new NormalData().getItems(recyclerView, BOARD_NAME, tagName);

                        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                new NormalData().getItems(recyclerView, BOARD_NAME, tagName);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                        givePathToParent(tagName);

                        break;

                }
            }
        });

        chipGroup.isLongClickable();
        chipGroup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (v.getId()){
                    case R.id.chipFreeTalk:
                        Toast.makeText(getContext(), "자유", Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.chipDIY:
                        Toast.makeText(getContext(), "DIY", Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.chipInterior:
                        Toast.makeText(getContext(), "인테리어", Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.chipTownInfo:
                        Toast.makeText(getContext(), "동네정보", Toast.LENGTH_LONG).show();
                        return true;
                }
                return true;
            }
        });

        return view;
    }

}