package com.example.woddy.Alarm;

import static java.sql.DriverManager.println;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.woddy.Chatting.ChattingFragment;
import com.example.woddy.Posting.ShowPosting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FcmPush { //푸시 보내기
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String url ="https://fcm.googleapis.com/fcm/send";
    String serverKey = "AIzaSyDSEptV5uYWh0W3qCKWUwbYYrw9DxmuMOU";
    //String serverKey = "AAAAt6rIneU:APA91bHEm4_X52q6SP4HHl8Io5Htm_9C_DLmWkBF-7UAAhZVLRp4uiIIzh9o1quYBGYGfEetQdkTmQ7UNFxS6sdojbbg8q3uoU5utTFApEjw0kdT9XYrGO2-5hHSBdP-HEBato9ApxX_";
    Gson gson = new Gson();
    OkHttpClient okHttpClient = new OkHttpClient();

    public static final FcmPush instance = new FcmPush();
    static Context context = FcmPush.context;


    FcmPush(){
        gson = new Gson();
        okHttpClient = new OkHttpClient();
    }

    public void sendMessage(String destinationUid, String title, String message) {
        FirebaseFirestore.getInstance().collection("pushtokens").document(destinationUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String token = task.getResult().get("pushToken").toString();
                            PushDTO pushDTO = new PushDTO();
                            pushDTO.setTo(token);
                            pushDTO.notification.setTitle(title);
                            pushDTO.notification.setBody(message);

                            RequestBody body = RequestBody.create(JSON, gson.toJson(pushDTO));
                            Request request;
                            request = new Request.Builder()
                                    .addHeader("Content-Type","application/json")
                                    .addHeader("Authorization","key="+serverKey)
                                    .url(url)
                                    .post(body)
                                    .build();

//                            Intent intent = null;
//                            if (pushDTO.click_action =="ShowPosting"){
//                                    intent = new Intent(context, ShowPosting.class);
//                                    intent.getStringExtra(PostingNum);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            }
//                            else if (pushDTO.click_action =="ChattingFragment"){
//                                intent = new Intent(context, ChattingFragment.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            }
//
//                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
//                                    PendingIntent.FLAG_ONE_SHOT);

                            //NetworkOnMainThreadException 해결해야함
                            try (Response response = okHttpClient.newCall(request).execute()) {
                            }catch(Exception e){
                                e.printStackTrace();
                            }

//                            okHttpClient.newCall(request).enqueue(new Callback() {
//                                @Override
//                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                                }
//
//                                @Override
//                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                                    println(response.toString());
//                                }
//                            });
//                        }
                    }
                }
        });
    }
}
