package com.example.woddy.Alarm;

import android.app.Activity;
import android.app.Notification;

import com.example.woddy.Posting.ShowPosting;

public class PushDTO { //푸시 데이터
    String to = null;
    Notification notification = new Notification();

    public String getTo() {
        return to;
    }

    public Notification getNotification() {
        return notification;
    }



    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setTo(String to) {
        this.to = to;
    }

    class Notification{
        String body = null;
        String title = null;

        public String getBody() {
            return body;
        }

        public String getTitle() {
            return title;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
