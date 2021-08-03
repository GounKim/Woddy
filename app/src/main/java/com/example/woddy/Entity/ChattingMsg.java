package com.example.woddy.Entity;

import java.util.HashMap;
import java.util.Map;

// 채팅 대화내용
public class ChattingMsg {
    private String roomNum; // 채팅방 번호[ FK : ChattingInfo ]
    private String writer; // 작성자
    private String message; // 대화내용
    private String writtenTime; // 작성시간

    public ChattingMsg() {}

    public ChattingMsg(String roomNum, String writer, String message, String writtenTime) {
        this.roomNum = roomNum;
        this.writer = writer;
        this.message = message;
        this.writtenTime = writtenTime;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> chatChat = new HashMap<>();
        chatChat.put("writer", writer);
        chatChat.put("message", message);
        chatChat.put("writtenTime", writtenTime);

        return chatChat;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String talk) {
        this.message = talk;
    }

    public String getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(String writtenTime) {
        this.writtenTime = writtenTime;
    }
}
