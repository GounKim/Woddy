package com.example.woddy.Notice.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woddy.Notice.NoticeAdapter;
import com.example.woddy.Notice.NoticeItem;
import com.example.woddy.Notice.NoticeMain;
import com.example.woddy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FreeFragment extends Fragment {

    private RecyclerView mVerticalView;
    private NoticeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ChipGroup chipGroup;

    public FreeFragment() {
        // Required empty public constructor
    }

    private ArrayList<NoticeItem> items = new ArrayList<>();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notice_page_free, container, false);
        context = container.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_free);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);
        //default 부분 - 시작 시
        initDataset();
        items.add(new NoticeItem("club", "같이 java 공부하실 분 찾아요", "쉐어게시판 ","16:30","1000000"));
        Toast.makeText(context,  "친구찾기 클릭", Toast.LENGTH_SHORT).show();

        mAdapter = new NoticeAdapter(context, items);
        recyclerView.setAdapter(mAdapter);

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipFreeTalk:
                        initDataset();
                        items.add(new NoticeItem("freetalk", "저녁뭐먹지", "자유게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "자유 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                    case R.id.chipDIY:
                        initDataset();
                        items.add(new NoticeItem("diy", "앨범이 들어가야할듯", "자유게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "DIY 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;
                    case R.id.chipInterior:
                        initDataset();
                        items.add(new NoticeItem("interior", "앨범필요", "자유게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "인테리어 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                    case R.id.chipTownInfo:
                        initDataset();
                        items.add(new NoticeItem("town_info", "동네정보", "자유게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "동네정보 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                }
            }
        });

        return view;
    }

    private void initDataset() { //들어갈 데이터
        //초기화
        items.clear();
        items.add(new NoticeItem("writer", "hello", "쉐어게시판 ","15:10","10000"));
        items.add(new NoticeItem("me", "nice to meet you", "쉐어게시판 ","15:07","105"));
        items.add(new NoticeItem("you", "hello world", "쉐어게시판 ","15:05","1000"));
    }

}