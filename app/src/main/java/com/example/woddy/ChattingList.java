package com.example.woddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ChattingList extends ActivityBase {
    RecyclerView recyclerView;
    ChattingListAdapter clAdapter;
    Button button;

    ArrayList<String> chatterList;    // 초기 채팅 리스트 (사용자이름)
    ArrayList<String> rChattingList;    // 초기 채팅 리스트 (최근채팅)
    ArrayList<Uri> cImageList; // 초기 채팅 리스트 (채팅상대 이미지)

    String image1 = "file://https://w7.pngwing.com/pngs/998/506/png-transparent-computer-icons-businessperson-organization-boys-and-girls-dormitory-icon-service-people-head.png";
    String image2 = "file://https://e7.pngegg.com/pngimages/758/183/png-clipart-computer-icons-icon-design-user-women-icon-cdr-hat.png";
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_list);

        setMyTitle("채팅목록");

        button = findViewById(R.id.button);
        recyclerView = (RecyclerView)findViewById(R.id.chatting_recycler_view);

        // 초기 채팅 리스트 설정
        chatterList = new ArrayList<>();
        rChattingList = new ArrayList<>();
        cImageList = new ArrayList<>();

        chatterList.add("사용자A");
        rChattingList.add("사용자A의 메시지");
        cImageList.add(Uri.parse(image1));
        chatterList.add("사용자B");
        rChattingList.add("사용자B의 메시지");
        cImageList.add(Uri.parse(image2));

        clAdapter = new ChattingListAdapter(this, chatterList, rChattingList,cImageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(clAdapter);

        String str = "사용자";
        String chat = "사용자의 메시지";
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num % 2 == 0) {
                    clAdapter.addItem(str, chat, Uri.parse(image1));
                }
                else {
                    clAdapter.addItem(str, chat, Uri.parse(image2));
                }
                num++;
            }
        });


    }
}

