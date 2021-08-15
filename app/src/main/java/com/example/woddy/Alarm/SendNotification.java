package com.example.woddy.Alarm;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//수신용
public class SendNotification {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static void sendNotification(String regToken, String title, String messsage){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... parms) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("body", messsage);
                    dataJson.put("title", title);
                    json.put("notification", dataJson);
                    json.put("to", regToken);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key="
                                    + "AAAAt6rIneU:APA91bHEm4_X52q6SP4HHl8Io5Htm_9C_DLmWkBF-7UAAhZVLRp4uiIIzh9o1quYBGYGfEetQdkTmQ7UNFxS6sdojbbg8q3uoU5utTFApEjw0kdT9XYrGO2-5hHSBdP-HEBato9ApxX_")
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                }catch (Exception e){
                    Log.d("error", e+"");
                }
                return  null;
            }
        }.execute();
    }

//    private void sendGson() {
//
//        FirebaseFirestore.getInstance().collection("pushtokens").document("Uid").get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful()) {
//                            String token = task.getResult().get("pushToken").toString();
//                            PushDTO pushDTO = new PushDTO();
//                            pushDTO.setTo(token);
//                            pushDTO.notification.setTitle(title);
//                            pushDTO.notification.setBody(message);
//                            SendNotification.sendNotification(mPushToken, profile.getNickName(), mFcmMessage);
//                        }
//
//
//        mRootDatabaseReference.child("UserList").child(mFriendUid).child("PushToken").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String,String> map= (Map<String, String>) dataSnapshot.getValue(); //상대유저의 토큰
//                mPushToken = map.get("pushToken");
//
//
//
//                Log.d(TAG, "상대방의 토큰 : " + mPushToken);
//                mRootDatabaseReference.child("UserList").child(mFriendUid).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Profile profile = dataSnapshot.getValue(Profile.class);
//                        SendNotification.sendNotification(mPushToken, profile.getNickName(), mFcmMessage);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}