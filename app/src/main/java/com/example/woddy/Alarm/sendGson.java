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

//알림 송신용
public class sendGson {

    private static FirebaseFirestore fsDB=FirebaseFirestore.getInstance();

    static String TAG = "sendgson";

    public static void sendGson(String destinationUid, String title, String message) {

        DocumentReference docRef = fsDB.collection("userProfile").document(destinationUid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> map = document.getData();
                        String mPushToken = map.get("pushToken").toString(); //상대유저의 토큰
                        Log.d(TAG, "mPushToken: " + mPushToken);
                        SendNotification.sendNotification(mPushToken, title, message);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
