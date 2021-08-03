package com.example.woddy.Entity;

// 게시판 태그
public class BoardTag {
    private String tagName; // 태그 이름
    private String BoardName; // 게시판 이름 [ FK : Board ]


    public BoardTag(String tagName, String boardName) {
        this.tagName = tagName;
        BoardName = boardName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getBoardName() {
        return BoardName;
    }

    public void setBoardName(String boardName) {
        BoardName = boardName;
    }
}
