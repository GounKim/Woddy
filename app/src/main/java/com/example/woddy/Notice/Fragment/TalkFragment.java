package com.example.woddy.Notice.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woddy.Notice.NoticeAdapter;
import com.example.woddy.Notice.NoticeItem;
import com.example.woddy.R;
import com.google.android.material.chip.ChipGroup;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TalkFragment extends Fragment {

    private RecyclerView mVerticalView;
    private NoticeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ChipGroup chipGroup;

    public TalkFragment() {
        // Required empty public constructor
    }


    private ArrayList<NoticeItem> items = new ArrayList<>();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notice_page_talk, container, false);
        context = container.getContext();

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipFriend:
                        items.add(new NoticeItem("frined", "제 친구를 소개합니다.", "소통게시판 ","16:30","1000000"));
                        initDataset();
                        Toast.makeText(context,  R.string.frind +"클릭", Toast.LENGTH_SHORT).show();
                    case R.id.chipHelp:
                        items.add(new NoticeItem("help", "화장실에 갇혔어요.. 도와주세요", "소통게시판 ","16:30","1000000"));
                        initDataset();
                        Toast.makeText(context,  R.string.help +"클릭", Toast.LENGTH_SHORT).show();
                    case R.id.chipMate:
                        items.add(new NoticeItem("mate", "중랑구 서울여대에서 같이 집에가실 분 구해요", "소통게시판 ","16:30","1000000"));
                        initDataset();
                        Toast.makeText(context,  R.string.mate +"클릭", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Talk);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new NoticeAdapter(context, items);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    /*사용할 데이터를 미리 준비해 놓는다. 준비하는 형태에 따라 구현방법이 조금 달라 질 수 있다.
    1. 수동으로 Item을 입력해서 추가 하도록 할 수 있다.
    2. 온라인에서 DB에서 일괄 가져 올 수 도 있다.
    어떻게든 itmes 배열에 데이터를 형식에 맞게 넣어 어답터와 연결만 하면 화면에 내용이 출력될것이다.
    */
    private void initDataset() {
        //초기화
        items.clear();
        items.add(new NoticeItem("writer", "hello", "소통게시판 ","15:10","10000"));
        items.add(new NoticeItem("me", "nice to meet you", "소통게시판 ","15:07","105"));
        items.add(new NoticeItem("you", "hello world", "소통게시판 ","15:05","1000"));
    }

}