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

    private int MAX_ITEM_COUNT = 50;

    // int max_button = 3; // 버튼 갯수 받아와 넣기
    LinearLayout View;

    ArrayList<NoticeItem> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notice_board);

        final Button btn1_Friend = (Button) findViewById(R.id.btn1_friend);
        final Button btn1_Help = (Button) findViewById(R.id.btn1_help);
        final Button btn1_Mate = (Button) findViewById(R.id.btn1_mate);

        final Button btn2_Diy = (Button) findViewById(R.id.btn2_diy);
        final Button btn2_Interior = (Button) findViewById(R.id.btn2_interior);
        final Button btn2_Towninfo = (Button) findViewById(R.id.btn2_towninfo);

        final Button btn3_Free = (Button) findViewById(R.id.btn3_free);
        final Button btn3_Home = (Button) findViewById(R.id.btn3_home);
        final Button btn3_Share = (Button) findViewById(R.id.btn3_share);
        final Button btn3_TGbuy = (Button) findViewById(R.id.btn3_tgbuy);

        final Button btn4_Daily = (Button) findViewById(R.id.btn4_daily);
        final Button btn4_Fandom = (Button) findViewById(R.id.btn4_fandom);
        final Button btn4_Freedom = (Button) findViewById(R.id.btn4_freedom);
        final Button btn4_Boast = (Button) findViewById(R.id.btn4_boast);

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



        mVerticalView = (RecyclerView) findViewById(R.id.vertical_list1);

        btn1_Friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

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
            }
        });
// 우선 하나에만 붙여놓고 나중에 다 즐겨찾기 넣기
        btn1_Friend.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(android.view.View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(NoticeMain.this);
                dlg.setTitle("즐겨찾기");
                dlg.setMessage("즐겨찾기에 등록하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                "즐겨찾기하시겠습니까?", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setPositiveButton("취소",null);
                return true;
            }

        });


        btn1_Mate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list1);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "퇴근각","퇴근퇴근퇴근퇴근퇴근퇴근퇴근",tabHost.getCurrentTabTag()));
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

            }
        });

        btn1_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mAdapter.notifyDataSetChanged();

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list1);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "help","Would you help me?",tabHost.getCurrentTabTag()));
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
            }
        });

        btn2_Diy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list2);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "DIY","이쁘게 만들기//앨범형",tabHost.getCurrentTabTag()));
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
            }
        });

        btn2_Interior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list2);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "앨범형 넣어야함","앨범형 넣기",tabHost.getCurrentTabTag()));
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
            }
        });

        btn2_Towninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list2);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "동네정보","동네정보",tabHost.getCurrentTabTag()));
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
            }
        });

        btn3_Free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list3);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "자유나눔","나눠ㅓㅓㅓ",tabHost.getCurrentTabTag()));
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
            }
        });


        btn3_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list3);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "홈","집 팔아요//앨범형",tabHost.getCurrentTabTag()));
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
            }
        });


        btn3_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list3);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "공유","나랑 물건 공유할사람?//앨범형",tabHost.getCurrentTabTag()));
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
            }
        });


        btn3_TGbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list3);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "공구","공구합니다//앨범형",tabHost.getCurrentTabTag()));
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
            }
        });

        btn4_Freedom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list4);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "freedom","도비는 자유에요",tabHost.getCurrentTabTag()));
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
            }
        });

        btn4_Boast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list4);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "내새끼 자랑","앨범형이 들어가야해요",tabHost.getCurrentTabTag()));
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
            }
        });

        btn4_Daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list4);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "일상","일상공유",tabHost.getCurrentTabTag()));
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
            }
        });

        btn4_Fandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mVerticalView = (RecyclerView) findViewById(R.id.vertical_list4);
                int i = 0;
                while (i < MAX_ITEM_COUNT) {
                    data.add(new NoticeItem(
                            "덕질","내새끼도 사랑이에요",tabHost.getCurrentTabTag()));
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
            }
        });


        // init LayoutManager


        /*



        for(int id = 0; id < max_button; id++){
            int num = id;
            Button button = new Button(this);
            button.setId(id);
            View.addView(button);

            button.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(android.view.View v) {

                    Toast.makeText(getApplicationContext(), num+"클릭", Toast.LENGTH_LONG).show();
                }
            });
        }
         */
    }
}