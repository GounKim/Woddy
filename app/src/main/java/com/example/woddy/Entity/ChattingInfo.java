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
    private String recentMsg;

    public ChattingInfo() {
    }

    public ChattingInfo(List<String> participant, List<String> participantImg) {
        this.roomNumber = "";
        this.participant = participant;
        this.participantImg = participantImg;
        this.recentMsg = "";
    }

    public ChattingInfo(List<String> participant) {
        this.roomNumber = "";
        this.participant = participant;
        this.participantImg = null;
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
}
