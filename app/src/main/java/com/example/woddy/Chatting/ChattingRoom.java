package com.example.woddy.Chatting;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import static android.content.ContentValues.TAG;

public class ChattingRoom extends BaseActivity {
    ChattingRoomAdapter crAdapter;
    RecyclerView crRecyclerView;
    ImageView btnPlus;
    EditText edtInputCon;
    Button btnSend;

    // DB
    DatabaseReference db;
    FirestoreManager manager;
    private ChildEventListener childEL; // 실시간 작업에 응답하기 위해 필요

    //private ArrayList<ChattingMsg> itemList;

    // 하단 메뉴 사용 안함
    @Override
    protected boolean useBottomNavi() {
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        // ChattingList에서 클릭한 방의 CHATTER 받아오기
        Intent intent = getIntent();
        String chatter = intent.getStringExtra("CHATTER");
        setMyTitle(chatter);
        String roomNum = intent.getStringExtra("ROOMNUM");
        String user = intent.getStringExtra("USER");


        initDatabase(roomNum);
        updateDB(roomNum);

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
                manager.addMessage(roomNum, new ChattingMsg(user, chat, new Date()));
                edtInputCon.setText(null);
            }
        });

    }

    private void initDatabase(String roomNum) {
        manager = new FirestoreManager();

        manager.getMessage(roomNum).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    ChattingMsg chattingMsg = document.toObject(ChattingMsg.class);
                                    crAdapter.addItem(chattingMsg);
                                    if(crAdapter.getItemCount() != 0) {
                                        crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount()-1);
                                    }
                                } catch (RuntimeException e){
                                    Log.d(TAG, "Error getting chatList: ", e);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void updateDB(String roomNum) {
        manager.getMessage(roomNum).limitToLast(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        for (DocumentSnapshot doc: value.getDocuments()) {
                            ChattingMsg chattingMsg = doc.toObject(ChattingMsg.class);
                            crAdapter.addItem(chattingMsg);
                            if(crAdapter.getItemCount() != 0) {
                                crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount()-1);
                            }
                        }
                    }
                });
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