package com.example.woddy.DB;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.User;
import com.example.woddy.Entity.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.woddy.Entity.UserActivity.WRITEARTICLE;

public class InitDBdata {
    FirebaseFirestore fsDB;
    FirestoreManager manager;
    int num = 0;

    public InitDBdata() throws Exception {
        fsDB = FirebaseFirestore.getInstance();
        manager = new FirestoreManager();
        //tv.setText("");

        //testUser();
//        testPosting();
        testChatList();

        /*
        // update 사용법
        Map<String, Object> data = new HashMap<>();
        data.put("local", "where do i live");
        manager.updateUser("user1", data);

        // delete 사용법
        manager.deleteUser("user2");

         */

    }

    private void testUser() throws Exception {
        User user1 = new User("user1", "사는 지역", "이미지");
        manager.addUser(user1);
        User user2 = new User("user2", "local", "image");
        manager.addUser(user2);
        User user3 = new User("user3", "local3", "image3");
        manager.addUser(user3);
/*
        // 작성한 글
        UserActivity userActivity1 = new UserActivity("P0000001", WRITEARTICLE);
        manager.addUserActivity("user1", userActivity1);
        UserActivity userActivity2 = new UserActivity("P0000002", WRITEARTICLE);
        manager.addUserActivity("user1", userActivity2);
        UserActivity userActivity3 = new UserActivity("P0000005", WRITEARTICLE);
        manager.addUserActivity("user1", userActivity3);

        UserActivity userActivity4 = new UserActivity("P0000004", WRITEARTICLE);
        manager.addUserActivity("user2", userActivity4);
        UserActivity userActivity5 = new UserActivity("P0000006", WRITEARTICLE);
        manager.addUserActivity("user2", userActivity5);
        UserActivity userActivity6 = new UserActivity("P0000008", WRITEARTICLE);
        manager.addUserActivity("user2", userActivity6);

        UserActivity userActivity7 = new UserActivity("P0000003", WRITEARTICLE);
        manager.addUserActivity("user3", userActivity7);
        UserActivity userActivity8 = new UserActivity("P0000007", WRITEARTICLE);
        manager.addUserActivity("user3", userActivity8);
        UserActivity userActivity9 = new UserActivity("P0000009", WRITEARTICLE);
        manager.addUserActivity("user3", userActivity9);

        // 좋아요한 글


        Map<String, Object> crData = new HashMap<>();
        crData.put("roomNum", "CR0000001");
        userDoc.collection("chattingRooms").add(crData);
        crData.clear();
        crData.put("roomNum", "CR0000002");
        userDoc.collection("chattingRooms").add(crData);
        crData.clear();


        crData.put("postingNum", "P0000003");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();
        crData.put("postingNum", "P0000001");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();

        crData.put("postingNum", "P0000005");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();
        crData.put("postingNum", "P0000006");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();

        crData.put("tagName", "BT0000001");
        crData.put("location","favoriteBoards");
        userDoc.collection("boardTag").add(crData);
        crData.clear();
        crData.put("tagName", "BT0000002");
        crData.put("location","favoriteBoards");
        userDoc.collection("boardTag").add(crData);
        crData.clear();

        //222
        userDoc = userRef.document("nickName2");
        userData.clear();
        userData.put("nickName", "사용자 닉네임");
        userData.put("local", "사는 지역");
        userData.put("introduce", "한줄 자기소개");
        userData.put("userImage", "image");
        userDoc.set(userData);

        crData.clear();
        crData.put("roomNum", "CR0000001");
        userDoc.collection("chattingRooms").add(crData);
        crData.clear();
        crData.put("roomNum", "CR0000002");
        userDoc.collection("chattingRooms").add(crData);
        crData.clear();

        userDoc.collection("posting");
        crData.put("postingNum", "P0000001");
        crData.put("location","myPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();
        crData.put("postingNum", "P0000002");
        crData.put("location","myPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();

        crData.put("postingNum", "P0000003");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();
        crData.put("postingNum", "P0000001");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();

        crData.put("postingNum", "P0000005");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();
        crData.put("postingNum", "P0000006");
        crData.put("location","likedPostings");
        userDoc.collection("posting").add(crData);
        crData.clear();

        crData.put("tagName", "BT0000001");
        crData.put("location","favoriteBoards");
        userDoc.collection("boardTag").add(crData);
        crData.clear();
        crData.put("tagName", "BT0000002");
        crData.put("location","favoriteBoards");
        userDoc.collection("boardTag").add(crData);
        crData.clear();
        */
    }

    private void testPosting() {
//        Posting posting1 = new Posting("태그 이름","user1","제목 작성1","오늘 날씨가 매우 덥네요.", new Date());
//        manager.addPosting(posting1);
//        Posting posting2 = new Posting("태그 이름","user2","제목 작성2","안녕하세요.", new Date());
//        manager.addPosting(posting2);
//        Posting posting3 = new Posting("태그 이름","user2","제목 작성3","오늘 날씨가 매우 춥네요.", new Date());
//        manager.addPosting(posting3);
//        Posting posting4 = new Posting("Tag Name","user3","제목 작성4","내용 작성.", new Date());
//        manager.addPosting(posting4);
//        Posting posting5 = new Posting("Tag Name","user1","제목 작성5","좋은 아침입니다.", "사진선택", new Date());
//        manager.addPosting(posting5);

//
//        Map<String, Object> tData = new HashMap<>();
//        tData.put("tagName", "BT0000001");
//        postDoc.collection("boardTag").add(tData);
//
//
//        tData.put("tagName", "BT0000001");
//        postDoc.collection("boardTag").add(tData);
//
//        tData.put("tagName", "BT0000001");
//        postDoc.collection("boardTag").add(tData);
    }

    private void testChatList() {
        //ChattingInfo chatInfo1 = new ChattingInfo("CR0000001", Arrays.asList("user1", "user2"));
        //manager.addChatRoom(chatInfo1);
        //ChattingInfo chatInfo2 = new ChattingInfo("CR0000002", Arrays.asList("user3", "user1"));
        //manager.addChatRoom(chatInfo2);
        //ChattingInfo chatInfo3 = new ChattingInfo("CR0000003", Arrays.asList("user2", "user3"));
        //manager.addChatRoom(chatInfo3);
        //ChattingInfo chatInfo4 = new ChattingInfo("CR0000004", Arrays.asList("user1", "user5"));
        //manager.addChatRoom(chatInfo4);
        ChattingInfo chatInfo5 = new ChattingInfo(Arrays.asList("user6", "user3"), Arrays.asList("UserProfileImages/user2_profile.jpg", "UserProfileImages/user2_profile.jpg"));
        manager.addChatRoom(chatInfo5);
    }
}
