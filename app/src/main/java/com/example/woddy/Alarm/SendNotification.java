package com.example.woddy.Alarm;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//알림 수신기 : 알림 받기
public class SendNotification {

    //json타입 선언
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //알림 받기
    public static void sendNotification(String regToken, String title, String messsage){

        //파이어베이스 FCM 서버 키
        String serverKey = "AAAAt6rIneU:APA91bHEm4_X52q6SP4HHl8Io5Htm_9C_DLmWkBF-7UAAhZVLRp4uiIIzh9o1quYBGYGfEetQdkTmQ7UNFxS6sdojbbg8q3uoU5utTFApEjw0kdT9XYrGO2-5hHSBdP-HEBato9ApxX_";

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... parms) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject(); // 푸시 정보
                    JSONObject dataJson = new JSONObject(); //내용

                    dataJson.put("body", messsage);
                    dataJson.put("title", title);

                    json.put("notification", dataJson); //푸시 내용
                    json.put("to", regToken); //푸시 보낼 곳

                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key="+ serverKey) //헤더 정보(서버 키)
                            .url("https://fcm.googleapis.com/fcm/send") //REST API 전송할 URL
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute(); //요청 후 대기하다 응답 반환
                    String finalResponse = response.body().string();
                }catch (Exception e){
                    Log.d("error", e+"");
                }
                return  null;
            }
        }.execute();
    }

}