package com.example.woddy.Chatting;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.media.Image;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.SQLiteManager;
import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ChattingRoom extends BaseActivity {

    ChattingRoomAdapter crAdapter;
    RecyclerView crRecyclerView;
    EditText edtInputCon;
    Button btnSend;
    ImageView toolbarLogoImage;

    // DB
    FirestoreManager manager;
    SQLiteManager sql;

    // 하단 메뉴 사용 안함
    @Override
    protected boolean useBottomNavi() {
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        sql = new SQLiteManager(getApplicationContext());
        manager = new FirestoreManager();


        toolbarLogoImage = (ImageView) findViewById(R.id.toolbar_logo);
        toolbarLogoImage.setVisibility(View.GONE);

        // ChattingList에서 클릭한 방의 CHATTER 받아오기
        Intent intent = getIntent();
        String chatter = intent.getStringExtra("CHATTER");
        setMyTitle(chatter);    // 채팅방 이름(toolbar 제목)을 상대방 이름으로 설정
        String roomNum = intent.getStringExtra("ROOMNUM");
        String user = sql.getUserNick();    // 사용자 닉네임 받아오기
        String chatterImage = intent.getStringExtra("IMAGE");

        initDatabase(roomNum);  // 기존 메시지 목록 가져오기
        updateDB(roomNum);  // 새로운 메시지 받았을때 업데이트

        // xml 연결
        crRecyclerView = findViewById(R.id.chatting_room_recyclerView);
        edtInputCon = findViewById(R.id.edt_input_conversation);
        btnSend = findViewById(R.id.btn_send);

        // ChattingRoomAdapter연결
        crAdapter = new ChattingRoomAdapter(user, chatter, chatterImage);
        crRecyclerView.setLayoutManager(new LinearLayoutManager(this, crRecyclerView.VERTICAL, false)); // 상하 스크롤
        crRecyclerView.setAdapter(crAdapter);
        if (crAdapter.getItemCount() != 0) {
            crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount() - 1);
        }

        // 메시지 전송 버튼 누를시
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chat = edtInputCon.getText().toString();
                manager.addMessage(roomNum, new ChattingMsg(user, chat, new Date()));   // 메시지 전송(DB)
                edtInputCon.setText(null);
            }
        });

    }

    // 기존 메시지 가져오기
    private void initDatabase(String roomNum) {
        manager.getMessage(roomNum).get()   // 채팅방에 해당하는 메시지 찾아 가져오기
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ChattingMsg> msgArrayList = new ArrayList<>();
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ChattingMsg chattingMsg = document.toObject(ChattingMsg.class);
                                    msgArrayList.add(chattingMsg);

                                    // 가장 아래로 스크롤
                                    if (crAdapter.getItemCount() != 0) {
                                        crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount() - 1);
                                    } 
                                }
                                crAdapter.setItem(msgArrayList);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    // 송신되는 메시지 대기
    private void updateDB(String roomNum) {
        manager.getMessage(roomNum).limitToLast(1)  // 송신된 메시지만 받아옴
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        for (DocumentSnapshot doc: value.getDocuments()) {
                            ChattingMsg chattingMsg = doc.toObject(ChattingMsg.class);
                            crAdapter.addItem(chattingMsg); // 수신한 메시지 화면에 추가

                            // 가장 아래로 스크롤
                            if(crAdapter.getItemCount() != 0) {
                                crRecyclerView.smoothScrollToPosition(crAdapter.getItemCount()-1);
                            }
                        }
                    }
                });
    }
}