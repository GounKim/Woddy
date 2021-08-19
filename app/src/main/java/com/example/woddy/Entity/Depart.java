package com.example.woddy.Entity;

public class Depart {
    private int postingNumber; //글 번호
    private String depart; //기관 이름
    private String tel; //기관 전화번호
    private String url; //기관 url
    private String program; //기관 프로그램명
    private String content; //글 내용
    private String logo;

    public Depart() {
    }

    public String getDepart() {
        return depart;
    }

    public String getTel() {
        return tel;
    }

    public String getUrl() {
        return url;
    }

    public String getProgram() {
        return program;
    }

    public String getContent() {
        return content;
    }

    public String getLogo() {
        return logo;
    }

    public void setContent(String str) {
        this.content = str;
    }


}
