package com.example.woddy.Entity;

import java.util.ArrayList;

// 게시판
public class Board {
    private String boardName;
    private ArrayList<String> tags;

    public Board(String boardName) {
        this.boardName = boardName;
        this.tags =  new ArrayList<>();
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
