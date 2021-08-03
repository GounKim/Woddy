package com.example.woddy.Home;

public class HomePostings {
    private String writer;
    private String shortContent;
    private String boardName;
    private int liked;
    private String writtenTime;       //private Date writtenTime;
    private String image;

    public HomePostings() {
    }

    public HomePostings(String writer, String shortContent, String boardName, int liked, String writtenTime) {
        this.writer = writer;
        this.shortContent = shortContent;
        this.boardName = boardName;
        this.liked = liked;
        this.writtenTime = writtenTime;
        this.image = "";
    }

    public HomePostings(String writer, String shortContent, String boardName, int liked, String writtenTime, String image) {
        this.writer = writer;
        this.shortContent = shortContent;
        this.boardName = boardName;
        this.liked = liked;
        this.writtenTime = writtenTime;
        this.image = image;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(String writtenTime) {
        this.writtenTime = writtenTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
