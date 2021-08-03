package com.example.woddy.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 사용자
public class User {
    private String nickName;    // 닉네임 [ FK : MemberInfo ]
    private String local;   // 지역(동)
    private String introduce;   // 한줄소개
    private String userImage;   // 사용자 이미지(사진)
    private String chattingList;    // 채팅목록
    private String myPostings;  // 작성한 게시물
    private String likedPostings;   // 좋아요 누른글
    private String scrappedPostings;    // 스크랩한글
    private String favoriteBoards;  // 즐겨찾기한 게시판

    public User(String nickName) {
        this.nickName = nickName;
        this.local = null;
        this.introduce = null;
        this.userImage = null;
        this.chattingList = null;
        this.myPostings = null;
        this.likedPostings = null;
        this.scrappedPostings = null;
        this.favoriteBoards = null;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getChattingList() {
        return chattingList;
    }

    public void setChattingList(String chattingList) {
        this.chattingList = chattingList;
    }


    public String getMyPostings() {
        return myPostings;
    }

    public void setMyPostings(String myPostings) {
        this.myPostings = myPostings;
    }

    public String getLikedPostings() {
        return likedPostings;
    }

    public void setLikedPostings(String likedPostings) {
        this.likedPostings = likedPostings;
    }

    public String getScrappedPostings() {
        return scrappedPostings;
    }

    public void setScrappedPostings(String scrappedPostings) {
        this.scrappedPostings = scrappedPostings;
    }

    public String getFavoriteBoards() {
        return favoriteBoards;
    }

    public void setFavoriteBoards(String favoriteBoards) {
        this.favoriteBoards = favoriteBoards;
    }
}

