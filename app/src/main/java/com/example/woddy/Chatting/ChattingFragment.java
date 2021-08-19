package com.example.woddy.Chatting;

import android.os.Bundle;

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
import com.example.woddy.DB.SQLiteManager;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;
import static com.example.woddy.DB.FirestoreManager.USER_UID;


public class ChattingFragment extends Fragment {
    FirestoreManager manager = new FirestoreManager();
    SQLiteManager sqlManager;

    RecyclerView recyclerView;
    ChattingAdapter clAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);

        sqlManager = new SQLiteManager(getContext());
        recyclerView = view.findViewById(R.id.chatting_recycler_view);

        getChatList();

        clAdapter = new ChattingAdapter(USER_UID);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(clAdapter);

        return view;
    }

    // 채팅 리스트 가져오기
    private void getChatList() {
        manager.getChatRoomList(USER_UID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            try {
                                ChattingInfo chatInfo = doc.toObject(ChattingInfo.class);
                                setCurrentMsg(chatInfo);
                            } catch (RuntimeException e) {
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

                        for (DocumentSnapshot doc : value.getDocuments()) {
                            String curMsg = doc.get("message").toString();
                            chatInfo.setRecentMsg(curMsg);
                            clAdapter.setCurMsg(chatInfo);
                        }
                    }
                });
    }
}