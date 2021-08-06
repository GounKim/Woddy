package com.example.woddy.Entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 게시글
public class Posting {
    private String postingNumber;  // P0000001부터 시작
    private String tag; // 태그 이름 [ FK : BoardTag ]
    private String writer;  // User의 nickName [ FK : MemberInfo ]
    private String title;   // 글의 제목
    private String content; // 글의 내용
    private String pictures;    // 첨부된 사진
    private Date postedTime;    // 작성된 시간

    public Posting() {
    }

    public Posting(String tag, String writer, String title, String content, Date postedTime) {
        this.postingNumber = "";
        this.tag = tag;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.postedTime = postedTime;
    }

    public Posting(String tag, String writer, String title, String content, String pictures, Date postedTime) {
        this.postingNumber = "";
        this.tag = tag;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.postedTime = postedTime;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> post = new HashMap<>();
        post.put("postingNumber", postingNumber);
        post.put("title", title);
        post.put("content", content);
        post.put("writer", writer);
        post.put("tag", tag);

        return post;
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

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }
}
