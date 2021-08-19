package com.example.woddy.Alarm;

import java.util.Date;

public class AlarmDTO { //알림 데이터
    String destinationUid = null; //알림 받을 사용자 uid
    String nickname = null; //알림 보낸 사용자 uid
    String postingPath = null; //포스팅 정보 저장

    Integer kind = null; //알림 종류 분류
    //0 : like alarm
    //1 : comment alarm
    //2 : chatting alarm

    String message = null; //알림에 띄울 문구
    Date timestamp = null; //시간

    public AlarmDTO() {
    }

    public String getPostingPath() {
        return postingPath;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDestinationUid() {
        return destinationUid;
    }

    public Integer getKind() {
        return kind;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDestinationUid(String destinationUid) {
        this.destinationUid = destinationUid;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setPostingPath(String postingPath) {
        this.postingPath = postingPath;
    }
}
