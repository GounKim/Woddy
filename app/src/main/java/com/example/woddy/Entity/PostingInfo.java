package com.example.woddy.Entity;

// 게시물 정보
public class PostingInfo {
    private String posing; // 글 번호 [ FK : Posting ]
    private String postedTime;    // 작성된 시간
    private int numberOfViews; // 조회수
    private int numberOfLiked;  // 좋아요 갯수
    private int numberOfScraped;    // 스크랩수
    private int numberOfComment;    // 댓글 수

    public PostingInfo(String posing, String postedTime) {
        this.posing = posing;
        this.postedTime = postedTime;
        this.numberOfViews = 0;
        this.numberOfLiked = 0;
        this.numberOfScraped = 0;
        this.numberOfComment = 0;
    }

    public String getPosing() {
        return posing;
    }

    public void setPosing(String posing) {
        this.posing = posing;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
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
}
