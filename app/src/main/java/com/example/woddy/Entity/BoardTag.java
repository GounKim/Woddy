package com.example.woddy.Entity;

import java.util.HashMap;
import java.util.Map;

// 게시판 태그
public class BoardTag {
    private String tagName; // 태그 이름
    private String BoardName; // 게시판 이름 [ FK : Board ]


    public BoardTag(String boardName, String tagName) {
        this.BoardName = boardName;
        this.tagName = tagName;
    }

    public Map<String, Object> BoardToMap() {
        HashMap<String, Object> boardMap = new HashMap<>();
        boardMap.put("BoardName", BoardName);

        return boardMap;
    }

    public Map<String, Object> tagToMap() {
        HashMap<String, Object> tagMap = new HashMap<>();
        tagMap.put("tagName", tagName);

        return tagMap;
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
