package com.example.woddy.DB;

import static android.content.ContentValues.TAG;
import static com.example.woddy.Alarm.MyFirebaseMessagingService.sendGson;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.woddy.Alarm.AlarmDTO;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.Entity.Comment;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.Profile;
import com.example.woddy.Entity.User;
import com.example.woddy.Entity.UserFavoriteBoard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirestoreManager {

    private FirebaseFirestore fsDB;
    SQLiteManager sqlmanager;
    private SQLiteManager sqlManager;

    public static final String USER_UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public FirestoreManager(Context context) {
        fsDB = FirebaseFirestore.getInstance();
        sqlManager = new SQLiteManager(context);
    }

    public FirestoreManager() {
        fsDB = FirebaseFirestore.getInstance();
    }

    // 현재 사용자 CollectionReference
    public CollectionReference userColRef(String userNick) {
        CollectionReference userColRef = fsDB.collection("users");
        return userColRef;
    }

    // 현재 사용자 프로필 CollectionReference
    public CollectionReference userProFileColRef(String userNick) {
        CollectionReference userProFileColRef = fsDB.collection("userProfiles");
        return userProFileColRef;
    }


    // Profile 추가 - 회원가입 시 유저 정보
    public void addProfile(String uid, Profile profile) {
        fsDB.collection("userProfile").document(uid).set(profile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User Profile has successfully Added!");
                        sqlmanager.setUser(uid, profile.getNickname(), profile.getUserImage());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in userProfile collection", e);
                    }
                });
    }

    // Profile 업데이트 (docID는 uid - 계정 생성 시 자동 부여되는 값)
    public void updateProfile(String uid, Map<String, Object> newData) {
        fsDB.collection("userProfile").document(uid).update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User Profile has successfully updated!");
                        String newNick = null, newImage = null;
                        if (newData.get("nickname") != null)
                            newNick = (String) newData.get("nickname");
                        if (newData.get("userImage") != null)
                            newImage = (String) newData.get("userImage");

                        if (newNick != null && newImage != null)
                            sqlmanager.setUser(uid, newNick, newImage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in userProfile collection", e);
                    }
                });
    }

    // Profile 삭제 (docID는 uid - 계정 생성 시 자동 부여되는 값)
    public void deleteProfile(String uid) {
        fsDB.collection("userProfile").document(uid).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "userProfile has successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting userProfile", e);
                    }
                });
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

    // uid로 사용자 닉네임 찾기
    public Task<DocumentSnapshot> findUserWithUid(String uid) {
        return fsDB.collection("userProfile").document(uid).get();
    }

    // 닉네임으로 사용자 찾기
    public Task<QuerySnapshot> findUserWithNick(String nickname) {
        return fsDB.collection("userProfile").whereEqualTo("nickname", nickname).get();
    }

    // 이메일 중복 확인
    public Task<QuerySnapshot> findEmail(String email) {
        return fsDB.collection("userProfile").whereEqualTo("userID", email).get();
    }

    // 닉네임 중복 확인
    public Task<QuerySnapshot> findNickname(String nickname) {
        return fsDB.collection("userProfile").whereEqualTo("nickname", nickname).get();
    }

    // User 추가
    public Task<QuerySnapshot> findUser(String userNick) {
        return fsDB.collection("user").whereEqualTo("nickName", userNick).get();
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

    /* ---------------------- Posting용 DB ---------------------- */
    // posting collectionRef
    public CollectionReference postCollectionRef(String boardName, String tagName) {
        CollectionReference postColRef = fsDB.collection("postBoard").document(boardName)
                .collection("postTag").document(tagName).collection("postings");
        return postColRef;
    }

    // 게시물 추가
    public void addPosting(String boardName, String tagName, Posting posting) {
        CollectionReference colRef = postCollectionRef(boardName, tagName);

        // postingUid=작성자 uid
//        String postingUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        posting.setPostingUid(postingUid);

        colRef.add(posting)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String path = documentReference.getPath();
                        // 이미지 Storage에 넣기
                        StorageManager storageManager = new StorageManager();
                        posting.setPictures(storageManager.addPostingImage(path, posting.getPictures()));
                        Log.d(TAG, "Posting has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in posting collection", e);
                    }
                });
    }

    // 게시물 내용 수정
    public void updatePosting(String postingPath, Map<String, Object> newData) {
        fsDB.document(postingPath).update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Posting has successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error updating posting", e);
                    }
                });
    }

    // 게시물 삭제
    public void delPosting(String postingPath) {
        StorageManager storage = new StorageManager();

        fsDB.document(postingPath).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Posting has successfully deleted!");

//                        // 사진 파일도 지우기
//                        int numOfPic = posting.getPictures().size();
//                        for (int index = 0; index < numOfPic; index++) {
//                            storage.delPostingImage(posting.getPictures().get(index));
//                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting posting", e);
                    }
                });
    }


    // 게시물 정보 수정
    public final static int INCRESE = 0;
    public final static int DECRESE = 1;
    public final static String LIKE = "numberOfLiked";
    public final static String SCRAP = "numberOfScraped";
    public final static String COMMEND = "numberOfComment";
    public final static String REPORT = "reported";

    final public void updatePostInfo(String postingPath, String field, int inORdecrese) {
        // 조회수, 스크랩 수 등 원하는 필드의 숫자 +1 하기
        Map<String, Object> data = new HashMap<>();
        if (inORdecrese == INCRESE) {
            data.put(field, FieldValue.increment(1));
        } else if (inORdecrese == DECRESE) {
            data.put(field, FieldValue.increment(-1));
        } else {
            Log.w(TAG, "Error updating postInfo");
            return;
        }

        fsDB.document(postingPath).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "postInfo has successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error updating postInfo", e);
                    }
                });

    }

    // 일반적인 경로로 게시물 불러오기
    public Query getPost(String boardName, String tagName) {
        return postCollectionRef(boardName, tagName).orderBy("postedTime", Query.Direction.DESCENDING);
    }

    // Path로 게시물 불러오기
    public DocumentReference getdocRefWithPath(String postingPath) {
        return fsDB.document(postingPath);
    }

    // postingNumber로 게시물 불러오기
    public Query getPostWithNum(String postingNum) {
        return fsDB.collectionGroup("postings").whereEqualTo("postingNumber", postingNum);
    }

    public Query getAllPosting(String searchWord){
        return fsDB.collectionGroup("postings").whereGreaterThanOrEqualTo("content",searchWord.toLowerCase());
    }
  
    // postingNumber로 게시물 불러오기
    public Query getPostWithWriter(String writer) {
        return fsDB.collectionGroup("postings").whereEqualTo("writer", writer).orderBy("postedTime", Query.Direction.DESCENDING);
    }

    // 최근 게시물 불러오기 (docID는 postingNumber)
    public Query getCurrentPost() {
        return fsDB.collectionGroup("postings").orderBy("postedTime", Query.Direction.DESCENDING).limit(3);
    }

    // 인기 게시물 불러오기
    public Query getPopularPost() {
        return fsDB.collectionGroup("postings").orderBy("numberOfLiked", Query.Direction.DESCENDING).limit(3);
    }

    // 게시물 댓글 추가 (postingNumber은 게시물 번호)
    public void addComment(String postingPath, Comment comment) {
        fsDB.document(postingPath)
                .collection("comments").add(comment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        //해당 게시물의 작성자 uid 획득
                        fsDB.document(postingPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Posting posting = document.toObject(Posting.class);
                                        String uid = posting.getPostingUid();
                                        commentAlarm(uid, comment.getContent(), postingPath); //댓글 알림
                                    }
                                }
                            }
                        });

                        Log.d(TAG, "Comment has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in comment collection", e);
                    }
                });
    }

    // 게시물 댓글 수정
    public void updateComment(String commentPath, Map<String, Object> newData) {
        fsDB.document(commentPath).update(newData)
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

    // 게시물 댓글 삭제
    public void delComment(String commentPath) {
        fsDB.document(commentPath).delete()
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

    // 댓글 불러오기
    public Query getComments(String postingPath) {
        return fsDB.document(postingPath).collection("comments").orderBy("writtenTime", Query.Direction.ASCENDING);  // 포스팅의 댓글 가져오기
    }


    /* ---------------------- 정보용 DB ---------------------- */
    // 정보글 불러오기
    public Task<DocumentSnapshot> getNews(String postTag) {
        return fsDB.collection("postBoard").document("정보")
                .collection("postTag").document(postTag).get();
    }

    public CollectionReference departColRef(String boardName) {
        CollectionReference departColRef = fsDB.collection("postBoard")
                .document(boardName).collection("postings");
        return departColRef;
    }

    public Query getNewsQuery(String boardName, String tagName) {
        return postCollectionRef(boardName, tagName).orderBy("postedTime", Query.Direction.DESCENDING);
    }

    public Query getDepartQuery(String boardName) {
        return departColRef(boardName).orderBy("depart", Query.Direction.ASCENDING);
    }


    /* ---------------------- Chatting용 DB ---------------------- */
    // 채팅방 추가
    public void addChatRoom(ChattingInfo chattingInfo) {
        fsDB.collection("chattingRoom").add(chattingInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String roomNum = documentReference.getId();
                        Map<String, Object> data = new HashMap<>();
                        data.put("roomNumber", documentReference.getId());
                        updateChatRoom(roomNum, data);

                        addMessage(roomNum,
                                new ChattingMsg(null, chattingInfo.getParticipant().get(0) + "님과 " + chattingInfo.getParticipant().get(1) + "님이 입장하셨습니다.", new Date()));
                        Log.d(TAG, "chattingRoom has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in user collection", e);
                    }
                });
    }

    // 채팅방 번호 설정용 update (docID는 Posting Number)
    public void updateChatRoom(String docID, Map<String, Object> newData) {
        fsDB.collection("chattingRoom").document(docID).update(newData)
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

                        //채팅 보낸 사람의 닉네임 획득
                        findUserWithUid(USER_UID)
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String nickname = (String) documentSnapshot.get("nickname");
                                        Log.d("chatalarm",nickname);
                                        forChatAlarm(roomRef,nickname,msg.getMessage()); //채팅 알림을 위한 함수 호출
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "Fail to find writer info");
                                    }
                                });
                    }
                });
    }

    // 채팅 메시지 가져오기
    public Query getMessage(String roomNum) {
        DocumentReference docRef = fsDB.collection("chattingRoom").document(roomNum);

        return docRef.collection("messages").orderBy("writtenTime", Query.Direction.ASCENDING);
    }

    /* ************* 검색 ************* */
    public void search(String colPath, String field, String value) {
        CollectionReference ref = fsDB.collection("user");

        ref.whereEqualTo("nickName", value).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    Posting posting = document.toObject(Posting.class);
                                } catch (RuntimeException e) {
                                    Log.d(TAG, "Error getting Objset: ", e);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    //좋아요 알림
    public void likeAlarm(String destinationUid, String postPath) {
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setDestinationUid(destinationUid);

        //좋아요 누른 사용자의 닉네임 획득
        findUserWithUid(USER_UID)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String nickname = (String) documentSnapshot.get("nickname");
                        Log.d("likealarm",nickname);
                        alarmDTO.setNickname(nickname);
                        alarmDTO.setPostingPath(postPath);
                        alarmDTO.setKind(0);
                        alarmDTO.setTimestamp(new Date());
                        String message = (alarmDTO.getNickname() + "님이 당신의 게시물에 좋아요를 눌렸습니다.");

                        //파이어베이스에 알림 등록
                        FirebaseFirestore.getInstance().collection("alarms").document().set(alarmDTO);

                        //푸시 알림 송신
                        sendGson(destinationUid, "Woddy", message);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Fail to find writer info");
                    }
                });
    }

    //댓글 알림
    public void commentAlarm(String destinationUid, String message, String postPath) {
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setDestinationUid(destinationUid);

        //댓글 단 사용자의 닉네임 획득
        findUserWithUid(USER_UID)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String nickname = (String) documentSnapshot.get("nickname");
                        Log.d("commentalarm",nickname);
                        alarmDTO.setNickname(nickname);
                        alarmDTO.setPostingPath(postPath);
                        alarmDTO.setKind(1);
                        alarmDTO.setMessage(message);
                        alarmDTO.setTimestamp(new Date());
                        String msg = (alarmDTO.getNickname() + "님이 당신의 게시물에 댓글을 달았습니다." + System.lineSeparator() + message);

                        //파이어베이스에 알림 등록
                        FirebaseFirestore.getInstance().collection("alarms").document().set(alarmDTO);

                        //푸시 알림 송신
                        sendGson(destinationUid, "Woddy", msg);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Fail to find writer info");
                    }
                });
    }

    //채팅 알림
    public void chattingAlarm(String destinationUid, String message, String roomNum) {
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setDestinationUid(destinationUid);

        //채팅 보낸 사용자의 닉네임 획득
        findUserWithUid(USER_UID)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String nickname = (String) documentSnapshot.get("nickname");
                        Log.d("chatalarm",nickname);
                        alarmDTO.setNickname(nickname);
                        alarmDTO.setKind(2);
                        alarmDTO.setMessage(message);
                        alarmDTO.setTimestamp(new Date());
                        alarmDTO.setRoomNum(roomNum);
                        String msg = (alarmDTO.getNickname() + "님에게 새로운 채팅이 왔습니다." + System.lineSeparator() + message);

                        ////파이어베이스에 알림 등록
                        FirebaseFirestore.getInstance().collection("alarms").document().set(alarmDTO);

                        //푸시 알림 송신
                        sendGson(destinationUid, "Woddy", msg);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Fail to find writer info");
                    }
                });
    }

    //uid 가져와서 채팅 알림 발생
    public void forChatAlarm(DocumentReference roomRef, String mynickname, String msg) {
        roomRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //자신의 닉네임 획득
                        String nickname = document.get("participant").toString();
                        nickname = nickname.replace(mynickname, "");
                        nickname = nickname.replace("[", "");
                        nickname = nickname.replace(", ", "");
                        nickname = nickname.replace("]", "");
                        Log.d("firebase", nickname);

                        //알림 받을 사용자의 uid 획득
                        fsDB.collection("userProfile")
                                .whereEqualTo("nickname", nickname).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if( task.isSuccessful()) {
                                            QuerySnapshot qDocument = task.getResult();
                                            String uid = qDocument.getDocuments().get(0).getId();
                                            String roomNum = document.get("roomNumber").toString();
                                            Log.d("firebase", uid);
                                            chattingAlarm(uid, msg,roomNum); //채팅 알림
                                        }else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
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