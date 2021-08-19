package com.example.woddy.Entity;

import java.util.ArrayList;

// 게시글
public class PostingSQL {
    private String postingPath;
    private String board;   // 게시판 이름
    private String tag; // 태그 이름 [ FK : BoardTag ]
    private String writer;  // User의 nickName [ FK : MemberInfo ]
    private String title;   // 글의 제목
    private String content; // 글의 내용
    private String postedTime;    // 작성된 시간
    private ArrayList<String> pictures;

    public PostingSQL() {
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

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPostingPath() {
        return postingPath;
    }

    public void setPostingPath(String postingPath) {
        this.postingPath = postingPath;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
}
