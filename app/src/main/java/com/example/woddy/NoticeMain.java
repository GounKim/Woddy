package com.example.woddy;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressWarnings("deprecation")

public class NoticeMain extends TabActivity {

    private RecyclerView mVerticalView;
    private NoticeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private int MAX_ITEM_COUNT = 10;// 정보 횟수 받아서 넣으면 됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String btnName1[] = { "#친구찾기", "#도움요청", "#퇴근메이트" };
        final String btnName2[] = { "#DIY", "#인테리어", "#동네정보" };
        final String btnName3[] = { "#물품공유", "#홈", "#공동구매","#무료나눔" };
        final String btnName4[] = { "#자유", "#일상", "#덕질","#자랑" };

        Integer btnID1[] = { 0,1,2 };
        Integer btnID2[] = { 3,4,5 };
        Integer btnID3[] = { 6,7,8,9 };
        Integer btnID4[] = { 10,11,12,13 };

        LinearLayout View_Talk = (LinearLayout) findViewById(R.id.talk_notice);
        LinearLayout View_Info = (LinearLayout) findViewById(R.id.info_notice);
        LinearLayout View_Share = (LinearLayout) findViewById(R.id.share_notice);
        LinearLayout View_Free = (LinearLayout) findViewById(R.id.free_notice);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecNotice1 = tabHost.newTabSpec("Notice1").setIndicator("소통게시판");
        tabSpecNotice1.setContent(R.id.notice1);

        tabHost.addTab(tabSpecNotice1);

        TabHost.TabSpec tabSpecNotice2 = tabHost.newTabSpec("Notice2").setIndicator("정보게시판");
        tabSpecNotice2.setContent(R.id.notice2);
        tabHost.addTab(tabSpecNotice2);

        TabHost.TabSpec tabSpecNotice3 = tabHost.newTabSpec("Notice3").setIndicator("쉐어게시판");
        tabSpecNotice3.setContent(R.id.notice3);
        tabHost.addTab(tabSpecNotice3);

        TabHost.TabSpec tabSpecNotice4 = tabHost.newTabSpec("Notice4").setIndicator("자유게시판");
        tabSpecNotice4.setContent(R.id.notice4);
        tabHost.addTab(tabSpecNotice4);

        tabHost.setCurrentTab(0);

        ArrayList<NoticeItem> data = new ArrayList<>();

        mVerticalView = (RecyclerView) findViewById(R.id.vertical_list1);
        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            data.add(new NoticeItem(
                    "hello","nice to meet you",tabHost.getCurrentTabTag()));
            i++;
        }
        mLayoutManager = new LinearLayoutManager(NoticeMain.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL

        // setLayoutManager
        mVerticalView.setLayoutManager(mLayoutManager);

        // init Adapter
        mAdapter = new NoticeAdapter();

        // set Data
        mAdapter.setData(data);

        // set Adapter
        mVerticalView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        for(int id = 0; id < 3; id++){
            int num = id;

            Button button = new Button(this);
            button.setText(btnName1[num]);
            View_Talk.addView(button);

            try {
                button.setId(btnID1[num]);
            }catch (NumberFormatException e) {

            }

            button.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(android.view.View v) {
                    mVerticalView = (RecyclerView) findViewById(R.id.vertical_list1);
                    int i = 0;
                    data.clear();
                    switch (v.getId()){
                        case 0:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "hello","nice to meet you",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 1:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "help","help me please",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 2:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "mate","your my mate",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        default:
                            Toast.makeText(getApplicationContext(), "오류발생", Toast.LENGTH_SHORT).show();
                    }
                    mLayoutManager = new LinearLayoutManager(NoticeMain.this);
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL

                    // setLayoutManager
                    mVerticalView.setLayoutManager(mLayoutManager);

                    // init Adapter
                    mAdapter = new NoticeAdapter();

                    // set Data
                    mAdapter.setData(data);

                    // set Adapter
                    mVerticalView.setAdapter(mAdapter);

                    Toast.makeText(getApplicationContext(), btnName1[num] +"클릭", Toast.LENGTH_SHORT).show();
                }
            });

            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(android.view.View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeMain.this);
                    dlg.setTitle("즐겨찾기");
                    dlg.setMessage("즐겨찾기에 등록하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),
                                    btnName1[num] + "즐겨찾기하시겠습니까?", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setPositiveButton("취소",null);
                    return false;
                }

            });
        }

        for(int id = 0; id < 3; id++){
            int num = id;
            Button button = new Button(this);
            try {
                button.setId(btnID2[num]);
            }catch (NumberFormatException e) {

            }
            button.setText(btnName2[num]);
            View_Info.addView(button);

            button.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(android.view.View v) {
                    mVerticalView = (RecyclerView) findViewById(R.id.vertical_list2);
                    int i = 0;
                    data.clear();
                    switch (v.getId()){
                        case 3:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "diy","앨범형을 넣어야합니다",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 4:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "inteior","앨범형을 넣어야합니다ㅏ",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 5:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "town_info","동네 정보를 얻어가세요",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        default:
                            Toast.makeText(getApplicationContext(), "오류발생", Toast.LENGTH_SHORT).show();
                    }
                    mLayoutManager = new LinearLayoutManager(NoticeMain.this);
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL

                    // setLayoutManager
                    mVerticalView.setLayoutManager(mLayoutManager);

                    // init Adapter
                    mAdapter = new NoticeAdapter();

                    // set Data
                    mAdapter.setData(data);

                    // set Adapter
                    mVerticalView.setAdapter(mAdapter);

                    Toast.makeText(getApplicationContext(), btnName2[num]+"클릭", Toast.LENGTH_SHORT).show();
                }
            });
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(android.view.View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeMain.this);
                    dlg.setTitle("즐겨찾기");
                    dlg.setMessage("즐겨찾기에 등록하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),
                                    btnName2[num] + "즐겨찾기하시겠습니까?", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setPositiveButton("취소",null);
                    return false;
                }

            });
        }

        for(int id = 0; id < 4; id++){
            int num = id;
            Button button = new Button(this);
            try {
                button.setId(btnID3[num]);
            }catch (NumberFormatException e) {

            }
            button.setText(btnName3[num]);
            View_Share.addView(button);

            button.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(android.view.View v) {
                    mVerticalView = (RecyclerView) findViewById(R.id.vertical_list3);
                    int i = 0;
                    data.clear();
                    switch (v.getId()){
                        case 6:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "물품공유","앨범형 넣어야해요",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 7:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "홈","앨범형",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 8:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "공동구매","공구구합니다ㅏㅏ",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 9:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "무료나눔","무나해요",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        default:
                            Toast.makeText(getApplicationContext(), "오류발생", Toast.LENGTH_SHORT).show();
                    }
                    mLayoutManager = new LinearLayoutManager(NoticeMain.this);
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL

                    // setLayoutManager
                    mVerticalView.setLayoutManager(mLayoutManager);

                    // init Adapter
                    mAdapter = new NoticeAdapter();

                    // set Data
                    mAdapter.setData(data);

                    // set Adapter
                    mVerticalView.setAdapter(mAdapter);
                    Toast.makeText(getApplicationContext(), btnName3[num]+"클릭", Toast.LENGTH_SHORT).show();
                }
            });

            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(android.view.View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeMain.this);
                    dlg.setTitle("즐겨찾기");
                    dlg.setMessage("즐겨찾기에 등록하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),
                                    btnName3[num] + "즐겨찾기하시겠습니까?", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setPositiveButton("취소",null);
                    return false;
                }

            });
        }

        for(int id = 0; id < 4; id++){
            int num = id;
            Button button = new Button(this);
            try {
                button.setId(btnID4[num]);
            }catch (NumberFormatException e) {

            }
            button.setText(btnName4[num]);
            View_Free.addView(button);

            button.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(android.view.View v) {
                    mVerticalView = (RecyclerView) findViewById(R.id.vertical_list4);
                    int i = 0;
                    data.clear();
                    switch (v.getId()){
                        case 10:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "자유","난 자유다ㅏㅏㅏ",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 11:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "일상","저녁이 뭘까요",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 12:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "덕질","내새끼 이뻐",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        case 13:
                            while (i < MAX_ITEM_COUNT) {
                                data.add(new NoticeItem(
                                        "자랑","고양이 키우고 싶다ㅏ",tabHost.getCurrentTabTag()));
                                i++;
                            }
                        default:
                            Toast.makeText(getApplicationContext(), "오류발생", Toast.LENGTH_SHORT).show();
                    }
                    mLayoutManager = new LinearLayoutManager(NoticeMain.this);
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL

                    // setLayoutManager
                    mVerticalView.setLayoutManager(mLayoutManager);

                    // init Adapter
                    mAdapter = new NoticeAdapter();

                    // set Data
                    mAdapter.setData(data);

                    // set Adapter
                    mVerticalView.setAdapter(mAdapter);
                    Toast.makeText(getApplicationContext(), btnName4[num]+"클릭", Toast.LENGTH_SHORT).show();
                }
            });

            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(android.view.View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeMain.this);
                    dlg.setTitle("즐겨찾기");
                    dlg.setMessage("즐겨찾기에 등록하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),
                                    btnName4[num] + "즐겨찾기하시겠습니까?", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setPositiveButton("취소",null);
                    return false;
                }

            });
        }
    }

}
