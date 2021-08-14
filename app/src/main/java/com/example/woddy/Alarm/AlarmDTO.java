package com.example.woddy.Alarm;

public class AlarmDTO { //알림 데이터
    String destinationUid = null;
    String userId = null;
    String uid = null;
    String postingNumber = null;
    String click_action = null;

    Integer kind = null;
    //0 : like alarm
    //1 : comment alarm
    //2 : chatting alarm

    String message = null;
    Long timestamp = null;

    public AlarmDTO(){
    }

    public String getClick_action() {
        return click_action;
    }

    public String getDestinationUid(){
        return destinationUid;
    }

    public String getUserId() {
        return userId;
    }

    public String getUid() {
        return uid;
    }

    public Integer getKind() {
        return kind;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPostingNumber() {
        return postingNumber;
    }

    public void setDestinationUid(String destinationUid) {
        this.destinationUid = destinationUid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUid(String uid) { this.uid = uid; }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setPostingNumber(String postingNumber) {
        this.postingNumber = postingNumber;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }
}
