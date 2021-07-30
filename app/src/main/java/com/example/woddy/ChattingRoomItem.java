package com.example.woddy;

public class ChattingRoomItem {
    private String conversation;    // 대화내용
    private String time;    // 수신 or 송신 시간
    private int viewType;   // 사용자가 보낸 메시지 or 상대방이 보낸 메시지 or 당일 날짜 메시지
    private String date;

    public ChattingRoomItem(String conversation, String time, int viewType) {
        this.conversation = conversation;
        this.time = time;
        this.viewType = viewType;
        date = null;
    }

    public ChattingRoomItem(String date) {
        this.date = date;
        viewType = 2;
        conversation = null;
        time = null;
    }

    public String getConversation() {
        return conversation;
    }

    public String getTime() {
        return time;
    }

    public int getViewType() {
        return viewType;
    }

    public String getDate() {
        return date;
    }
}
