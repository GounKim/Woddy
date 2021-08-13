package com.example.woddy.Alarm;

public class AlarmDTO {
    String destinationUid = null;
    String userId = null;
    String uid = null;

    Integer kind = null;
    //0 : like alarm
    //1 : comment alarm
    //2 : chatting alarm

    String message = null;
    Long timestamp = null;

    public AlarmDTO(){
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

    public void setDestinationUid(String destinationUid) {
        this.destinationUid = destinationUid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
