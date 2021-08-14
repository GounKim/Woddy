package com.example.woddy.Entity;

// 회원 정보
public class Profile {

    private String userID; // id(email)
    private String nickname; // nickname
    private String city; // 시
    private String gu; // 구
    private String dong; // 동
    private String local; //전체 주소
    private String userImage; //프로필 사진
    private boolean women; // 여성 인증 여부

    public Profile(String userID, String nickname, String city, String gu, String dong, String local) {
        this.userID = userID;
        this.nickname = nickname;
        this.city = city;
        this.gu = gu;
        this.dong = dong;
        this.local = local;
        this.userImage = "UserProfileImages/user.png";
        this.women = false;
    }

    public String getUserID() {
        return userID;
    } //set은 필요 없을듯

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

    public boolean isWomen() {
        return women;
    }

    public void setWomen(boolean women) {
        this.women = women;
    }
}
