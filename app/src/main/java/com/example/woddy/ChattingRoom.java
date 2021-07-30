package com.example.woddy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirebaseManager;
import com.example.woddy.Entity.ChattingMsg;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChattingRoom extends ActivityBase{
    ChattingRoomAdapter crAdapter;
    RecyclerView crRecyclerView;
    ImageView btnPlus;
    EditText edtInputCon;
    Button btnSend;

    // DB
    DatabaseReference db;
    FirebaseManager firebaseManager;
    private ChildEventListener childEL; // 실시간 작업에 응답하기 위해 필요

    //private ArrayList<ChattingMsg> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        // ChattingList에서 클릭한 방의 CHATTER 받아오기
        Intent intent = getIntent();
        String chatter = intent.getStringExtra("CHATTER");
        setMyTitle(chatter);

        String user = "userB";

        initDatabase();
        //Toast.makeText(getApplicationContext(), firebaseManager.getChat().get(0), Toast.LENGTH_LONG).show();
        //firebaseManager.updateUser();

        // xml 연결
        crRecyclerView = findViewById(R.id.chatting_room_recyclerView);
        btnPlus = findViewById(R.id.btn_plus);
        edtInputCon = findViewById(R.id.edt_input_conversation);
        btnSend = findViewById(R.id.btn_send);

        // ChattingRoomAdapter연결
        crAdapter = new ChattingRoomAdapter(chatter, user);
        crRecyclerView.setLayoutManager(new LinearLayoutManager(this, crRecyclerView.VERTICAL, false)); // 상하 스크롤
        crRecyclerView.setAdapter(crAdapter);
        if(crAdapter.getItemCount() != 0) {
            crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount() - 1);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chat = edtInputCon.getText().toString();
                firebaseManager.addChattingChat(new ChattingMsg("CR0000001", user, chat,timestamp()));
                edtInputCon.setText(null);
                //crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount()-1);
            }
        });

    }

    private void initDatabase() {
        db = FirebaseDatabase.getInstance().getReference();
        firebaseManager = new FirebaseManager(db);

        //firebaseManager.addChattingRoom(initInfo());

        childEL = new ChildEventListener() {
            // 데이터가 추가되었을 때
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                ChattingMsg chattingMsg = snapshot.getValue(ChattingMsg.class);
                crAdapter.addItem(chattingMsg);
                if(crAdapter.getItemCount() != 0) {
                    crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount()-1);
                }
            }

            //데이터가 변경되었을 띠
            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            // 데이터가 제거되었을 때
            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            // 데이터의 DB 리스트 위치가 변경되었을 때
            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            // DB 처리 중 오류가 발생했을 때
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        db.child("chattingRoom")
                .child("CR0000001")
                .child("messages").orderByChild("writtenTime").addChildEventListener(childEL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(childEL);
    }

    private String timestamp() {    // 타임스탬프 생성
        TimeZone timeZone;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

/*
    private ChattingInfo initInfo() {
        ChattingInfo chatInfo = new ChattingInfo("CR0000001", "userA", "userB");
        return chatInfo;
    }

    private void initChatdb() {
        ChattingMsg chatChat1 = new ChattingMsg("CR0000001", "userA", "A의 메시지입니다.", "15:30");
        firebaseManager.addChattingChat(chatChat1);
        ChattingMsg chatChat2 = new ChattingMsg("CR0000001", "userB", "B의 메시지입니다.", "15:30");
        firebaseManager.addChattingChat(chatChat2);
        ChattingMsg chatChat3 = new ChattingMsg("CR0000001", "userA", "A의 대화입니다..", "15:30");
        firebaseManager.addChattingChat(chatChat3);
    }

    private void initChat() {
        itemList = new ArrayList<>();
        itemList.add(new ChattingRoomItem("상대방의 대화입니다.", "7:15", 1));
        itemList.add(new ChattingRoomItem("당신의 대화입니다.", "8:40", 0));
        itemList.add(new ChattingRoomItem("상대방이 말하고 있습니다.", "17:05", 1));
        itemList.add(new ChattingRoomItem("2021-07-23"));
        itemList.add(new ChattingRoomItem("상대방이 채팅치고 있습니다.", "8:23", 1));
        itemList.add(new ChattingRoomItem("당신이 채팅하였습니다.", "8:24", 0));
        itemList.add(new ChattingRoomItem("상대방의 대화입니다.", "7:15", 1));
        itemList.add(new ChattingRoomItem("당신의 대화입니다.", "8:40", 0));
        itemList.add(new ChattingRoomItem("상대방이 말하고 있습니다.", "17:05", 1));
        itemList.add(new ChattingRoomItem("상대방이 채팅치고 있습니다.", "8:23", 1));
        itemList.add(new ChattingRoomItem("당신이 채팅하였습니다.", "8:24", 0));
    } */
}