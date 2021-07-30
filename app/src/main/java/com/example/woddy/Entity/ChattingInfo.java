package com.example.woddy.Entity;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

// 채팅방 정보
public class ChattingInfo {
    private String roomNumber;  // 채팅방 번호 ( CR0000001부터 )
    private String userA;   // 참가자1 [ FK : User ]
    private String userB;   // 참가자2 [ FK : User ]
    private String messages;

    public ChattingInfo(String roomNumber, String userA, String userB) {
        this.roomNumber = roomNumber;
        this.userA = userA;
        this.userB = userB;
        this.messages = null;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> chatInfo = new HashMap<>();
        chatInfo.put("userAName", userA);
        chatInfo.put("userBName", userB);
        chatInfo.put("messages", null);

        return chatInfo;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getUserA() {
        return userA;
    }

    public void setUserA(String userA) {
        userA = userA;
    }

    public String getUserB() {
        return userB;
    }

    public void setUserB(String user2) {
        userB = userB;
    }
}
