package com.example.woddy.Alarm;

import android.util.Log;
import android.view.autofill.AutofillId;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class sendGson {

    private static FirebaseFirestore fsDB=FirebaseFirestore.getInstance();

    static String TAG = "sendgson";

    public static void sendGson(String title, String message) {

        DocumentReference docRef = fsDB.collection("pushtokens").document(FirebaseAuth.getInstance().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> map = document.getData(); //상대유저의 토큰
                        String mPushToken = map.get("pushToken").toString();
                        Log.d(TAG, "mPushToken: " + mPushToken);
                        SendNotification.sendNotification(mPushToken, "title", "메시지가 도착했습니다.");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


//        fsDB.collectionGroup("pushtokens").whereEqualTo("uid", FirebaseAuth.getInstance().getUid()).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Map<String, String> map = (Map<String, String>) task.getResult(); //상대유저의 토큰
//                            String mPushToken = map.get("pushToken");
//                            SendNotification.sendNotification(mPushToken, FirebaseAuth.getInstance().getUid(), "메시지가 도착했습니다.");
//                            Log.d(TAG, mPushToken +":"+FirebaseAuth.getInstance().getUid()+":");
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }
}
