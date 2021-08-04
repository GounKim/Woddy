package com.example.woddy.Entity;

// 회원 정보
public class UserProfile {

    private String userID; // id(email)
    private String userPW; // password
    private String nickname; // nickname
    private String city; // 시
    private String gu; // 구
    private String dong; // 동
    private boolean women; // 여성 인증 여부

    public UserProfile(String userID, String userPW, String nickname, String city, String gu, String dong) {
        this.userID = userID;
        this.userPW = userPW;
        this.nickname = nickname;
        this.city = city;
        this.gu = gu;
        this.dong = dong;
        this.women = false;
    }

    public String getUserID() {
        return userID;
    } //set은 필요 없을듯

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String password) {
        this.userPW = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGu() {
        return gu;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public boolean isWomen() {
        return women;
    }

    public void setWomen(boolean women) {
        this.women = women;
    }
}
