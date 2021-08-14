package com.example.woddy.DB;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    private final FirebaseStorage stroage;
    private final StorageReference storageRef;
    private final FirestoreManager manager;

    public StorageManager() {
        this.manager = new FirestoreManager();
        this.stroage = FirebaseStorage.getInstance();
        this.storageRef = stroage.getReference();
    }

    public String setProfileImage(String userNick, String uriPath, String uid) {
        String filename = userNick + "_profile.jpg"; // 파일명 생성: 사용자의 NickName_profile.jpg
        String fileUri = "UserProfileImages/" + userNick + "/" + filename;

        Uri file = Uri.fromFile(new File(uriPath));     // 절대경로(uri)를 file에 할당
        StorageReference riversRef = storageRef.child(fileUri);
        UploadTask uploadTask = riversRef.putFile(file);

        delProfileImage(userNick);  // 이미지가 존재하면 기존 이미지 삭제 후 진행할 수 있도록 삭제해준다.

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Uploading Image to stroage has failed!");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Uploading Image to stroage has successed!");

                Map<String, Object> uriData = new HashMap<>();
                uriData.put("userImage", "UserProfileImages/" + userNick + "/" + filename);
                manager.updateProfile(uid, uriData);
            }
        });

        return fileUri;
    }

    public void delProfileImage(String fileUri) {
        StorageReference desertRef = storageRef.child(fileUri);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "deleting Image in stroage has successed!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "deleting Image in stroage has failed!");
            }
        });
    }

    public ArrayList<String> addPostingImage(String postingNum, ArrayList<String> uriPath) {
        String storagePath = "BoardName/TagName/PostingImages/" + postingNum + "/";
        ArrayList<String> newUri = new ArrayList<>();

        for (int index = 0; index < uriPath.size(); index++) {
            String fileName = "picture" + index + ".jpg";

            Uri file = Uri.parse(uriPath.get(index));     // 절대경로(uri)를 file에 할당
            StorageReference riversRef = storageRef.child(storagePath + fileName);
            UploadTask uploadTask = riversRef.putFile(file);
            newUri.add(storagePath + fileName);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Uploading Image to stroage has failed!");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "Uploading Image to stroage has successed!");
                }
            });
        }

        return newUri;
    }

    public void delPostingImage(String fileUri) {
        StorageReference desertRef = storageRef.child(fileUri);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "deleting Image in stroage has successed!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "deleting Image in stroage has failed!");
            }
        });
    }
}
