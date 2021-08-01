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
    private String recentMsg;
    private String chatterImg;

    public ChattingInfo() {
    }

    public ChattingInfo(String roomNumber, List<String> participant) {
        this.roomNumber = roomNumber;
        this.participant = participant;
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

    public String getChatterImg() {
        return chatterImg;
    }

    public void setChatterImg(String chatterImg) {
        this.chatterImg = chatterImg;
    }
}
