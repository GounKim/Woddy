package com.example.woddy.Entity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 사용자
public class User {
    private String nickName;    // 닉네임 [ FK : MemberInfo ]
    private String local;   // 지역(동)
    private String userImage;   // 사용자 이미지(사진)    // private ArrayList<String> chattingList;    // 채팅목록
    private ArrayList<String> myPostings;  // 작성한 게시물
    private ArrayList<String> likedPostings;   // 좋아요 누른글
    private ArrayList<String> scrappedPostings;    // 스크랩한글
    private ArrayList<String> favoriteBoards;  // 즐겨찾기한 게시판

    public User() {
    }

    public User(String nickName) {
        this.nickName = nickName;
    }

    public User(String nickName, String local, String userImage) {
        this.nickName = nickName;
        this.local = local;
        this.userImage = userImage;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("nickName", nickName);

        return userMap;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


}

