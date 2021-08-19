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

    ChipGroup chipGroup;

    ImageButton searchBtn, beforeBtn; //버튼
    EditText searchText; // 검색창
    RecyclerView recyclerView; // 검색 내용

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

        chipGroup = (ChipGroup) findViewById(R.id.filterChipGroup);

        // 이전 버튼이 눌렸을 때 메인화면으로
        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 어떤 칩 그룹을 선택했을 때 그에 맞는 게시판 찾아가기
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
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

        // 검색 버튼을 누르면
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(boardType);
                if (boardType == "normal") { // 일반의 경우
                    new SearchData().getItems(recyclerView, board_name, tagName, searchText.getText().toString());
                } else if (boardType == "album") { //앨범형의 경우
                    new SearchAlbumData().getItems(recyclerView,board_name, tagName, searchText.getText().toString());
                } else {// 정보형의 경우
                    new SearchInfoData().getItems(recyclerView, board_name, tagName, searchText.getText().toString());
                }
            }
        });
    }

}