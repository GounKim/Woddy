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

public class ShareFragment extends Fragment {

    private RecyclerView mVerticalView;
    private NoticeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ChipGroup chipGroup;

    public ShareFragment() {
        // Required empty public constructor
    }

    private ArrayList<NoticeItem> items = new ArrayList<>();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notice_page_share, container, false);
        context = container.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_share);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup = (ChipGroup) view.findViewById(R.id.filterChipGroup);
        //default 부분 - 시작 시
        initDataset();
        items.add(new NoticeItem("share", "물건을 나눔합니다.", "쉐어게시판 ","16:30","1000000"));
        Toast.makeText(context,  "친구찾기 클릭", Toast.LENGTH_SHORT).show();

        mAdapter = new NoticeAdapter(context, items);
        recyclerView.setAdapter(mAdapter);

        // chip들 중 선택된 버튼이 무엇인가에 따라
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chipShare:
                        initDataset();
                        items.add(new NoticeItem("share", "고데기 빌려주실분 찾아요", "쉐어게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "친구찾기 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                    case R.id.chipHome:
                        initDataset();
                        items.add(new NoticeItem("home", "쉐어하우스", "쉐어게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "홈 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;

                    case R.id.chipBuy:
                        initDataset();
                        items.add(new NoticeItem("buy", "공구구해요", "쉐어게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "공동구매 클릭", Toast.LENGTH_SHORT).show();

                        mAdapter = new NoticeAdapter(context, items);
                        recyclerView.setAdapter(mAdapter);
                        break;
                        
                    case R.id.chipFreeShare:
                        initDataset();
                        items.add(new NoticeItem("freeshare", "무나합니다", "쉐어게시판 ","16:30","1000000"));
                        Toast.makeText(context,  "무료나눔 클릭", Toast.LENGTH_SHORT).show();

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