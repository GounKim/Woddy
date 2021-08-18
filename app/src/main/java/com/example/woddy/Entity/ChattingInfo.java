package com.example.woddy.Entity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 채팅방 정보
public class ChattingInfo {
    private String roomNumber;  // 채팅방 번호 ( CR0000001부터 )
    private List<String> participant;
    private List<String> participantImg;
    private List<String> participantUid; //채팅 참여자 uid
    private String recentMsg;

    public ChattingInfo() {
    }

    public ChattingInfo(List<String> participant, List<String> participantImg, List<String> participantUid) {
        this.roomNumber = "";
        this.participant = participant;
        this.participantImg = participantImg;
        this.participantUid = participantUid;
        this.recentMsg = "";
    }

    public ChattingInfo(List<String> participant, List<String> participantUid) {
        this.roomNumber = "";
        this.participant = participant;
        this.participantImg = null;
        this.participantUid = participantUid;
        this.recentMsg = "";
    }


    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }


    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }

    public String getRecentMsg() {
        return recentMsg;
    }

    public void setRecentMsg(String recentMsg) {
        this.recentMsg = recentMsg;
    }

    public List<String> getParticipantImg() {
        return participantImg;
    }

    public void setParticipantImg(List<String> participantImg) {
        this.participantImg = participantImg;
    }

    public List<String> getParticipantUid() {
        return participantUid;
    }

    public void setParticipantUid(List<String> participantUid) {
        this.participantUid = participantUid;
    }
}
