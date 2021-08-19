package com.example.woddy.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.woddy.MainActivity;
import com.example.woddy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


public class SearchActivity extends AppCompatActivity {

    ChipGroup chipGroup_info, chipGroup_board;
    Chip friend, help, mate, share, home, buy, freeShare, free, diy, interior, townInfo, club, meeting;

    ImageButton searchBtn, beforeBtn;
    EditText searchText;
    RecyclerView recyclerView;

    private String board_name = "정보";
    private String tagName = "생활지원";
    private String boardType = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        searchText = (EditText) findViewById(R.id.searchWord);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        beforeBtn = (ImageButton) findViewById(R.id.beforebtn);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        chipGroup_info = (ChipGroup) findViewById(R.id.filterChipGroup_info);
        chipGroup_board = (ChipGroup) findViewById(R.id.filterChipGroup_board);

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        chipGroup_info.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chipLifeSupport:
                        board_name = "소통";
                        tagName = "친구찾기";
                        boardType = "info";
                        break;
                    case R.id.chipEducation:
                        board_name = "소통";
                        tagName = "도움요청";
                        boardType = "info";
                        break;

                    case R.id.chipEvent:
                        board_name = "소통";
                        tagName = "퇴근메이트";
                        boardType = "info";
                        break;
                }
            }
        });

        chipGroup_board.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {

                    /*
                    case R.id.chipAll:
                        board_name = "전체";
                        tagName = "전체";
                        break;
                     */
                    case R.id.chipFriend:
                        board_name = "소통";
                        tagName = "친구찾기";
                        boardType = "normal";
                        break;

                    case R.id.chipHelp:
                        board_name = "소통";
                        tagName = "도움요청";
                        boardType = "normal";
                        break;

                    case R.id.chipMate:
                        board_name = "소통";
                        tagName = "퇴근메이트";
                        boardType = "normal";
                        break;

                    case R.id.chipShare:
                        board_name = "쉐어";
                        tagName = "물품공유";
                        boardType = "normal";
                        break;

                    case R.id.chipHome:
                        board_name = "쉐어";
                        tagName = "홈";
                        boardType = "album";
                        break;

                    case R.id.chipBuy:
                        board_name = "쉐어";
                        tagName = "공동구매";
                        boardType = "album";
                        break;

                    case R.id.chipFreeShare:
                        board_name = "쉐어";
                        tagName = "무료나눔";
                        boardType = "album";
                        break;

                    case R.id.chipClub:
                        board_name = "취미";
                        tagName = "동호회";
                        boardType = "normal";
                        break;

                    case R.id.chipMeeting:
                        board_name = "취미";
                        tagName = "번개모임";
                        boardType = "normal";
                        break;

                    case R.id.chipFreeTalk:
                        board_name = "자유";
                        tagName = "자유";
                        boardType = "normal";
                        break;

                    case R.id.chipDIY:
                        board_name = "자유";
                        tagName = "DIY";
                        boardType = "album";
                        break;

                    case R.id.chipInterior:
                        board_name = "자유";
                        tagName = "인테리어";
                        boardType = "album";
                        break;

                    case R.id.chipTownInfo:
                        board_name = "자유";
                        tagName = "동네정보";
                        boardType = "normal";
                        break;
                    default:
                        Toast.makeText(SearchActivity.this, "태그를 선택해주세요", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(boardType);
                if (boardType == "normal") {
                    new SearchData().getItems(recyclerView, board_name, tagName, searchText.getText().toString());
                } else if (boardType == "album") {
                    new SearchAlbumData().getItems(recyclerView, board_name, tagName, searchText.getText().toString());
                } else {
                    new SearchInfoData().getItems(recyclerView, board_name, tagName, searchText.getText().toString());
                }
            }
        });
    }

}