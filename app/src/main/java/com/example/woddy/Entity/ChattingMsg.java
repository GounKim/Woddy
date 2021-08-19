package com.example.woddy.Entity;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 채팅 대화내용
public class ChattingMsg {
    //private String roomNum; // 채팅방 번호[ FK : ChattingInfo ]
    private String writer; // 작성자
    private String message; // 대화내용
    private Date writtenTime; // 작성시간

    public ChattingMsg() {
    }

    public ChattingMsg(String writer, String message, Date writtenTime) {
        this.writer = writer;
        this.message = message;
        this.writtenTime = writtenTime;
    }

    public ChattingMsg(String message) {
        this.message = message;
    }

    public ChattingMsg(Date writtenTime) {
        this.writtenTime = writtenTime;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> chatChat = new HashMap<>();
        chatChat.put("writer", writer);
        chatChat.put("message", message);
        chatChat.put("writtenTime", writtenTime);

        return chatChat;
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

    public Date getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(Date writtenTime) {
        this.writtenTime = writtenTime;
    }
}
