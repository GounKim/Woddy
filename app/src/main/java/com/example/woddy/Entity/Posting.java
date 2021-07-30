package com.example.woddy.Entity;

// 게시글
public class Posting {
    private String postingNumber;  // P0000001부터 시작
    private String tag; // 태그 이름 [ FK : BoardTag ]
    private String writer;  // User의 nickName [ FK : MemberInfo ]
    private String title;   // 글의 제목
    private String content; // 글의 내용
    private String pictures;    // 첨부된 사진
    private int report; // 신고 여부 (신고된 횟수)

    public Posting(String postingNumber, String tag, String writer, String title, String content) {
        this.postingNumber = postingNumber;
        this.tag = tag;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public String getPostingNumber() {
        return postingNumber;
    }

    public void setPostingNumber(String postingNumber) {
        this.postingNumber = postingNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
