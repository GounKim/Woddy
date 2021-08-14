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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Talk);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);
        //default 부분 - 시작 시
        initDataset();
        items.add(new NoticeItem("frined", "제 친구를 소개합니다.", "소통게시판 ","16:30","1000000"));
        Toast.makeText(context,  "친구찾기 클릭", Toast.LENGTH_SHORT).show();

        mAdapter = new NoticeAdapter(context, items);
        recyclerView.setAdapter(mAdapter);

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipFriend:
                        initDataset();
                        items.add(new NoticeItem("frined", "제 친구를 소개합니다.", "소통게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "친구찾기 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                    case R.id.chipHelp:
                        initDataset();
                        items.add(new NoticeItem("help", "화장실에 갇혔어요.. 도와주세요", "소통게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "도움요청 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                    case R.id.chipMate:
                        initDataset();
                        items.add(new NoticeItem("mate", "중랑구 서울여대에서 같이 집에가실 분 구해요", "소통게시판 ","16:30","1000000"));
                       Toast.makeText(context,  "퇴근메이트 클릭", Toast.LENGTH_SHORT).show();

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
        items.add(new NoticeItem("writer", "hello", "소통게시판 ","15:10","10000"));
        items.add(new NoticeItem("me", "nice to meet you", "소통게시판 ","15:07","105"));
        items.add(new NoticeItem("you", "hello world", "소통게시판 ","15:05","1000"));
    }

}