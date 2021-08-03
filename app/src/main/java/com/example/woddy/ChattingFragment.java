package com.example.woddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.InitDBdata;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.Entity.ChattingMsg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class ChattingFragment extends Fragment {
    FirestoreManager manager = new FirestoreManager();

    RecyclerView recyclerView;
    ChattingListAdapter clAdapter;
    Button button;
    EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);

        recyclerView = view.findViewById(R.id.chatting_recycler_view);
        button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editText.getText().toString();
                // getDB
                getChatList(user);

                clAdapter = new ChattingListAdapter(view.getContext(), user);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
                recyclerView.setAdapter(clAdapter);
            }
        });

        return view;
    }

    // 채팅 리스트 가져오기
    private void getChatList(String user) {
        manager.getChatRoomList(user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        for (QueryDocumentSnapshot doc: value) {
                            try {
                                ChattingInfo chatInfo = doc.toObject(ChattingInfo.class);
                                setCurrentMsg(chatInfo);
                                Log.d(TAG, "!!!!!!" + chatInfo.getRoomNumber());
                            } catch (RuntimeException e){
                                Log.d(TAG, "Error getting chatList: ", e);
                            }
                        }
                    }
                });
    }

    // 최근 메시지 가져오기
    private void setCurrentMsg(ChattingInfo chatInfo) {
        manager.getMessage(chatInfo.getRoomNumber()).limitToLast(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        for (DocumentSnapshot doc: value.getDocuments()) {
                            String curMsg = doc.get("message").toString();
                            chatInfo.setRecentMsg(curMsg);
                            clAdapter.setCurMsg(chatInfo);
                        }
                    }
                });
    }
}