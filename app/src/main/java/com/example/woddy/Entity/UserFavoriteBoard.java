package com.example.woddy.Entity;

public class UserFavoriteBoard {
    String tagName;
    private String activityName;

    public UserFavoriteBoard(String tagName) {
        this.tagName = tagName;
        this.activityName = "favoriteBoards";
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getActivityName() {
        return activityName;
    }
}
