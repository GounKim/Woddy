package com.example.woddy;

public class NoticeItem {

    private String writer;
    private String message;
    private String boardName;


    public NoticeItem(String writer, String message, String boardName){

        this.writer = writer;
        this.message = message;
        this.boardName = boardName;
    }


    public String getWriter() {
        return writer;
    }

    public String getMessage() { return message; }

    public String getBoardName() {
        return boardName;
    }

}


