package com.example.woddy;

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

import com.example.woddy.DB.FirestoreManager;
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

    ArrayList<String> chatterList;    // 초기 채팅 리스트 (사용자이름)
    ArrayList<String> rChattingList;    // 초기 채팅 리스트 (최근채팅)
    ArrayList<Uri> cImageList; // 초기 채팅 리스트 (채팅상대 이미지)

    String image1 = "file://https://w7.pngwing.com/pngs/998/506/png-transparent-computer-icons-businessperson-organization-boys-and-girls-dormitory-icon-service-people-head.png";
    String image2 = "file://https://e7.pngegg.com/pngimages/758/183/png-clipart-computer-icons-icon-design-user-women-icon-cdr-hat.png";
    int num = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);

        recyclerView = view.findViewById(R.id.chatting_recycler_view);


        clAdapter = new ChattingListAdapter(view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(clAdapter);

        // getDB
        getChatList();

        // Inflate the layout for this fragment
        return view;
    }

    // 채팅 리스트 가져오기
    private void getChatList() {
        //FirestoreManager manager = new FirestoreManager();
        String user = "user1";

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