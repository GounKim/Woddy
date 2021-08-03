package com.example.woddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
/*
        TabHost tabHost = getTabHost();

        TabSpec tabSpecNotice1 = tabHost.newTabSpec("Notice1").setInicator("게시판1");
        tabSpecNotice1.setContent(R.id.notice1);
        tabHost.addTab(tabSpecNotice1);

        TabSpec tabSpecNotice2 = tabHost.newTabSpec("Notice2").setInicator("게시판2");
        tabSpecNotice2.setContent(R.id.notice2);
        tabHost.addTab(tabSpecNotice2);

        TabSpec tabSpecNotice3 = tabHost.newTabSpec("Notice3").setInicator("게시판3");
        tabSpecNotice3.setContent(R.id.notice3);
        tabHost.addTab(tabSpecNotice3);

        tabHost.setCurrentTab(0);

        ListView listView1 = findViewById(R.id.listView1);
        ListView listView2 = findViewById(R.id.listView2);
        ListView listView3 = findViewById(R.id.listView3);

        List<String> list = new ArrayList<>();
        list.add("사과");
        list.add("배");
        list.add("딸기");
        list.add("감자");

        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView1.setAdapter(adpater);
*/
    }


}