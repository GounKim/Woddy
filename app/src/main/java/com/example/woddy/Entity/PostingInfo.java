package com.example.woddy.Entity;

// 게시물 정보
public class PostingInfo {
    private int numberOfViews; // 조회수
    private int numberOfLiked;  // 좋아요 갯수
    private int numberOfScraped;    // 스크랩수
    private int numberOfComment;    // 댓글 수
    private int reported; // 신고 여부 (신고된 횟수)

    public PostingInfo() {
        this.numberOfViews = 0;
        this.numberOfLiked = 0;
        this.numberOfScraped = 0;
        this.numberOfComment = 0;
        this.reported = 0;
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
}
