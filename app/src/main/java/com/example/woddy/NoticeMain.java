package com.example.woddy;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;

public class NoticeMain extends TabActivity {

    private RecyclerView mRecyclerView;
    private NoticeAdapter noticeAdapter;
    private ArrayList<NoticeItem> mNoticeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notice_board);
/*
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecNotice1 = tabHost.newTabSpec("Notice1").setInicator("게시판1");
        tabSpecNotice1.setContent(R.id.notice1);
        tabHost.addTab(tabSpecNotice1);

        TabHost.TabSpec tabSpecNotice2 = tabHost.newTabSpec("Notice2").setInicator("게시판2");
        tabSpecNotice2.setContent(R.id.notice2);
        tabHost.addTab(tabSpecNotice2);

        TabHost.TabSpec tabSpecNotice3 = tabHost.newTabSpec("Notice3").setInicator("게시판3");
        tabSpecNotice3.setContent(R.id.notice3);
        tabHost.addTab(tabSpecNotice3);

        tabHost.setCurrentTab(0);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
*/
        /* initiate adapter */
        // noticeAdapter = new NoticeAdapter();

        /* initiate recyclerview */
        //  mRecyclerView.setAdapter(noticeAdapter);
        //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        /* adapt data */
        // mNoticeItems = new ArrayList<>();
        // mNoticeItems.add(new NoticeItem("hello","hello nice to meet you","board1",10,5000);

        // noticeAdapter.setNoticeList(mNoticeItems);

    }
}