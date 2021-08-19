package com.example.woddy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 게시글
public class Posting {
    private String postingNumber;  // P0000001부터 시작
    private String postingUid; //작성자의 uid
    private String tag; // 태그 이름 [ FK : BoardTag ]
    private String writer;  // User의 nickName [ FK : MemberInfo ]
    private String title;   // 글의 제목
    private String content; // 글의 내용
    private ArrayList<String> pictures;    // 첨부된 사진
    private Date postedTime;    // 작성된 시간
    private int numberOfViews = 0; // 조회수
    private int numberOfLiked = 0;  // 좋아요 갯수
    private int numberOfScraped = 0;    // 스크랩수
    private int numberOfComment = 0;    // 댓글 수
    private int reported = 0; // 신고 여부 (신고된 횟수)

    public Posting() {
    }

    public Posting(String writer, String title, String content, Date postedTime, String uid) {
        this.postingNumber = "";
        this.postingUid = uid;
        this.tag = tag;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.postedTime = postedTime;
        this.pictures = new ArrayList<>();
    }

    public Posting(String writer, String title, String content, ArrayList<String> pictures, Date postedTime, String uid) {
        this.postingNumber = "";
        this.postingUid = uid;
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
        post.put("postingUid", postingUid);
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


    public String getPostingUid() {
        return postingUid;
    }

    public void setPostingUid(String postingUid) {
        this.postingUid = postingUid;
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

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public int getNumberOfLiked() {
        return numberOfLiked;
    }

    public void setNumberOfLiked(int numberOfLiked) {
        this.numberOfLiked = numberOfLiked;
    }

    public int getNumberOfScraped() {
        return numberOfScraped;
    }

    public void setNumberOfScraped(int numberOfScraped) {
        this.numberOfScraped = numberOfScraped;
    }

    public int getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(int numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    public int getReported() {
        return reported;
    }

    public void setReported(int reported) {
        this.reported = reported;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
}
