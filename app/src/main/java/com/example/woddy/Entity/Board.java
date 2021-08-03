package com.example.woddy.Entity;

// 게시판
public class Board {
    private String boardName;

    public Board(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
