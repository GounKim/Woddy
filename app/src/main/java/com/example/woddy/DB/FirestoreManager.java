package com.example.woddy.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.Entity.MemberInfo;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.User;
import com.example.woddy.Entity.UserActivity;
import com.example.woddy.Entity.UserFavoriteBoard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.woddy.Entity.UserActivity.WRITEARTICLE;

public class FirestoreManager {
    private FirebaseFirestore fsDB;

    public FirestoreManager() {
        fsDB = FirebaseFirestore.getInstance();
    }

    // User 추가
    public void addUser(User user) {
        fsDB.collection("user").document(user.getNickName()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }

    // User 업데이트 (docID는 userNick)
    public void updateUser(String docID, Map<String, Object> newData) {
        fsDB.collection("user").document(docID).update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error updating user", e);
                    }
                });
    }

    // User 삭제 (docID는 userNick)
    public void deleteUser(String docID) {
        fsDB.collection("user").document(docID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "user has successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting user", e);
                    }
                });
    }

    // 사용자 활동(글쓰기, 좋아요, 스크랩) 추가
    public void addUserActivity(String docID, UserActivity activity) {
        DocumentReference userRef = fsDB.collection("user").document(docID);
        userRef.collection("posting").add(activity)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }

    // 사용자 활동(글쓰기, 좋아요, 스크랩) 추가
    public void delUserActivity(String docID, int activityName, DocumentReference userRef) {
        if (activityName == WRITEARTICLE) {
            // 목록에서 삭제 + 전체 posting에서 삭제 + 다른 사용자에게도 삭제된 메시지라고 떠야함


        }
        else {
            // 목록에서 삭제 구현 필요
        }
    }

    // 사용자가 즐겨찾기한 보드 설정
    public void addUFavorBoard(String docID, UserFavoriteBoard favorBoard) {
        DocumentReference userRef = fsDB.collection("user").document(docID);
        userRef.collection("boardTag").add(favorBoard)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }


    // Member 정보 추가 (회원가입용 사용자 정보 -> 접근 보안 上)
    public void addMember(MemberInfo member) {
        fsDB.collection("memberInfo").document(member.getNickname()).set(member)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }


    // Member 정보 수정 (docID는 userNick)
    public void updateMember(String docID, Map<String, Object> newData) {
        fsDB.collection("memberInfo").document(docID).update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error updating user", e);
                    }
                });
    }

    // Member 정보 삭제 (docID는 userNick)
    public void deleteMember(String docID) {
        fsDB.collection("memberInfo").document(docID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "user has successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting user", e);
                    }
                });
    }

    // 게시판 & 테그 추가
    public void addBoard(BoardTag boardTag) {
        fsDB.collection("boardTag").document(boardTag.getBoardName()).set(boardTag)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }

    // 게시판 & 테그 삭제
    public void deleteBoard(String tagName) {
        fsDB.collection("memberInfo").document(tagName).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "user has successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting user", e);
                    }
                });
    }

    // 게시물 추가
    public void addPosting(Posting posting) {
        fsDB.collection("posting").add(posting)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }

    // 게시물 수정 (docID는 Posting Number)
    public void updatePosting(String docID, Map<String, Object> newData) {
        fsDB.collection("posting").document(docID).update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error updating user", e);
                    }
                });
    }

    // 게시물 삭제 (docID는 userNick)
    public void delPosting(String docID) {
        fsDB.collection("posting").document(docID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "user has successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting user", e);
                    }
                });
    }

    // 채팅방 추가
    public void addChatRoom(ChattingInfo chattingInfo) {
        fsDB.collection("chattingRoom").document(chattingInfo.getRoomNumber()).set(chattingInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }

    // 채팅방 삭제
    public void delChatRoom(String docID) {
        fsDB.collection("chattingRoom").document(docID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "user has successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting user", e);
                    }
                });
    }

    // 사용자 채팅방 목록 설정
    public Query getChatRoomList(String user) {
        CollectionReference ref = fsDB.collection("chattingRoom");

        return ref.whereArrayContains("participant", user);
    }

    // 채팅 메시지 추가
    public void addMessage(String docID, ChattingMsg msg) {
        DocumentReference roomRef = fsDB.collection("chattingRoom").document(docID);
        roomRef.collection("messages").add(msg)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Message has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in Message collection", e);
                    }
                });
    }

    // 채팅 메시지 가져오기
    public Query getMessage(String roomNum) {
        DocumentReference docRef = fsDB.collection("chattingRoom").document(roomNum);

        return docRef.collection("messages").orderBy("writtenTime", Query.Direction.ASCENDING);
    }

//    // 채팅 마지막 메시지 가져오기
//    public Query getRecentMsg(String roomNum) {
//        DocumentReference docRef = fsDB.collection("chattingRoom").document(roomNum);
//
//        return docRef.collection("messages").orderBy("writtenTime", Query.Direction.DESCENDING);
//    }

    /* ************* 검색 ************* */
    public void search(String colPath, String field, String value) {
        CollectionReference ref = fsDB.collection(colPath);

        ref.whereEqualTo(field, value).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    Posting posting = document.toObject(Posting.class);
                                } catch (RuntimeException e){
                                    Log.d(TAG, "Error getting Objset: ", e);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    // 게시판 태그에 맞는 정렬할 게시물 검색


}

























