package com.example.woddy.Entity;

public class Comment {
    private String docID;
    private String writer;  // User의 nickName [ FK : MemberInfo ]
    private String content; // 글의 내용
    private String writtenTime; // 작성된 시간
    private int numberOfLiked;  // 좋아요 갯수
    private int numberOfComment;    // 대댓글 수

    public Comment() {
    }

    public Comment(String writer, String content, String writtenTime) {
        this.docID = "";
        this.writer = writer;
        this.content = content;
        this.writtenTime = writtenTime;
        this.numberOfLiked = 0;
        this.numberOfComment = 0;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(String writtenTime) {
        this.writtenTime = writtenTime;
    }

    public int getNumberOfLiked() {
        return numberOfLiked;
    }

    public void setNumberOfLiked(int numberOfLiked) {
        this.numberOfLiked = numberOfLiked;
    }

    public int getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(int numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
