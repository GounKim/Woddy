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
    Chip friend, help, mate, share, home, buy, freeShare, free, diy, interior, townInfo, club, meeting;

    ImageButton searchBtn,beforeBtn;
    EditText searchText;
    RecyclerView recyclerView;

    private String board_name = "소통";
    private String tagName = "친구찾기";

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

        friend = (Chip) findViewById(R.id.chipFriend);
        help = (Chip) findViewById(R.id.chipHelp);
        mate = (Chip) findViewById(R.id.chipMate);

        home = (Chip) findViewById(R.id.chipHome);
        share = (Chip) findViewById(R.id.chipShare);
        buy = (Chip) findViewById(R.id.chipBuy);
        freeShare = (Chip) findViewById(R.id.chipFreeShare);

        chipGroup = (ChipGroup) findViewById(R.id.filterChipGroup);
        meeting = (Chip) findViewById(R.id.chipMeeting);
        club = (Chip) findViewById(R.id.chipClub);

        free = (Chip) findViewById(R.id.chipFreeTalk);
        diy = (Chip) findViewById(R.id.chipDIY);
        interior = (Chip) findViewById(R.id.chipInterior);
        townInfo = (Chip) findViewById(R.id.chipTownInfo);

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chipFriend:
                        board_name = "소통";
                        tagName = "친구찾기";
                        break;

                    case R.id.chipHelp:
                        board_name = "소통";
                        tagName = "도움요청";
                        break;

                    case R.id.chipMate:
                        board_name = "소통";
                        tagName = "퇴근메이트";
                        break;

                    case R.id.chipShare:
                        board_name = "쉐어";
                        tagName = "물품공유";
                        break;

                    case R.id.chipHome:
                        board_name = "쉐어";
                        tagName = "홈";
                        break;

                    case R.id.chipBuy:
                        board_name = "쉐어";
                        tagName = "공동구매";
                        break;

                    case R.id.chipFreeShare:
                        board_name = "쉐어";
                        tagName = "무료나눔";
                        break;

                    case R.id.chipClub:
                        board_name = "취미";
                        tagName = "동호회";
                        break;

                    case R.id.chipMeeting:
                        board_name = "취미";
                        tagName = "번개모임";
                        break;

                    case R.id.chipFreeTalk:
                        board_name = "자유";
                        tagName = "자유";
                        break;

                    case R.id.chipDIY:
                        board_name = "자유";
                        tagName = "DIY";
                        break;

                    case R.id.chipInterior:
                        board_name = "자유";
                        tagName = "인테리어";
                        break;

                    case R.id.chipTownInfo:
                        board_name = "자유";
                        tagName = "동네정보";
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
                new SearchData(SearchActivity.this).getItems(recyclerView, board_name, tagName, searchText.getText().toString());
            }
        });
    }

}