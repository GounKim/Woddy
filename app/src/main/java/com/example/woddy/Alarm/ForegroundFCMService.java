package com.example.woddy.Alarm;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ForegroundFCMService extends FirebaseMessagingService {

    String TAG = "foreground";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification() != null){
            Intent intent = new Intent();
            intent.setAction("com.package.notification");
            sendBroadcast(intent);
        }
        else{
            Log.d(TAG, "getNotification null");
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
