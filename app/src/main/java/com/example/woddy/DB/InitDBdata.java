package com.example.woddy.DB;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import java.util.HashMap;
import java.util.Map;

import static android.service.controls.ControlsProviderService.TAG;
import static com.example.woddy.Entity.UserActivity.WRITEARTICLE;

public class InitDBdata {
    FirebaseFirestore fsDB;
    FirestoreManager manager;
    int num = 0;

    public InitDBdata(TextView tv) {
        fsDB = FirebaseFirestore.getInstance();
        manager = new FirestoreManager();
        tv.setText("");

        //testUser();
        //testPosting();

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
        User user1 = new User("user1", "사는 지역", "한줄 자기소개", "이미지");
        manager.addUser(user1);
        User user2 = new User("user2", "local", "introduce", "image");
        manager.addUser(user2);
        User user3 = new User("user3", "local3", "introduce3", "image3");
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
        CollectionReference postRef = fsDB.collection("/posting");

        DocumentReference postDoc = postRef.document("posting1");
        Map<String, Object> postData = new HashMap<>();
        postData.put("postingNum", "P0000001");
        postData.put("tag", "태그 이름");
        postData.put("writer", "nickName");
        postData.put("title", "제목 작성");
        postData.put("content", "내용 작성");
        postData.put("pictures", "사진 선택");
        postData.put("report", 0);
        postDoc.set(postData);

        Map<String, Object> tData = new HashMap<>();
        tData.put("tagName", "BT0000001");
        postDoc.collection("boardTag").add(tData);

        postData.clear();
        tData.clear();
        postDoc = postRef.document("posting2");
        postData.put("postingNum", "P0000002");
        postData.put("tag", "태그 이름");
        postData.put("writer", "nickName");
        postData.put("title", "제목 작성");
        postData.put("content", "내용 작성");
        postData.put("pictures", "사진 선택");
        postData.put("report", 0);
        postDoc.set(postData);

        tData.put("tagName", "BT0000001");
        postDoc.collection("boardTag").add(tData);

        postData.clear();
        tData.clear();
        postDoc = postRef.document("posting3");
        postData.put("postingNum", "P0000003");
        postData.put("tag", "태그 이름");
        postData.put("writer", "nickName");
        postData.put("title", "제목 작성");
        postData.put("content", "내용 작성");
        postData.put("pictures", "사진 선택");
        postData.put("report", 0);
        postDoc.set(postData);

        tData.put("tagName", "BT0000001");
        postDoc.collection("boardTag").add(tData);
    }
}
