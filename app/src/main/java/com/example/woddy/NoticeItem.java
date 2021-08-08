package com.example.woddy;

public class NoticeItem {
    private String writer;
    private String message;
    private String boardName;
    private int time;
    private int heartNum;

    public NoticeItem(String writer, String message, String boardName, int time, int heartNum) {
        this.writer = writer;
        this.message = message;
        this.boardName = boardName;
        this.time = time;
        this.heartNum = heartNum;
    }


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getHeartNum() {
        return heartNum;
    }

    public void setHeartNum(int heartNum) {
        this.heartNum = heartNum;
    }
}

