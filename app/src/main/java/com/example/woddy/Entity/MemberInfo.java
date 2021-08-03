package com.example.woddy.Entity;

// 회원 정보
public class MemberInfo {
    private String name;    // 이름
    private String nickname;    // 닉네임
    private String email;   // 이메일
    private String password;    // 비밀번호
    private boolean womanCertified; // 여성인증여부

    public MemberInfo(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.womanCertified = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWomanCertified() {
        return womanCertified;
    }

    public void setWomanCertified(boolean womanCertified) {
        this.womanCertified = womanCertified;
    }
}
