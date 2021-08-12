package com.example.woddy.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.woddy.Entity.BoardTag;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.Entity.UserProfile;
import com.example.woddy.Entity.Comment;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.woddy.Entity.UserActivity.WRITEARTICLE;

public class FirestoreManager {

    private FirebaseFirestore fsDB;

    public FirestoreManager() {
        fsDB = FirebaseFirestore.getInstance();
    }

    // User Profile 추가 - 회원가입 시 유저 정보
    public void addUserProfile(String uid, UserProfile userProfile) {
        fsDB.collection("userProfile").document(uid).set(userProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User Profile has successfully Added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in userProfile collection", e);
                    }
                });
    }

    // User Profile 업데이트 (docID는 uid - 계정 생성 시 자동 부여되는 값)
    public void updateUserProfile(String uid, Map<String, Object> newData) {
        fsDB.collection("userProfile").document(uid).update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "User Profile has successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error adding document in userProfile collection", e);
                    }
                });
    }

    // User Profile 삭제 (docID는 uid - 계정 생성 시 자동 부여되는 값)
    public void deleteUserProfile(String uid) {
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


        } else {
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

    // 게시판 & 테그 추가 (중복찾기 추가 필요)
    public void addBoard(BoardTag boardTag) {
        DocumentReference docRef = fsDB.collection("postBoard").document(boardTag.getBoardName());
        docRef.collection("postTag").document(boardTag.getTagName()).set(boardTag.tagToMap())
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
    // 수정필요
/*
    // 게시판 & 테그 수정
    public void updateBoard(String docID, Map<String, Object> newData) {
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
*/

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
        colRef.add(posting)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String pNum = documentReference.getId();
                        // 이미지 Storage에 넣기
                        StorageManager storageManager = new StorageManager();
                        posting.setPictures(storageManager.addPostingImage(pNum, posting.getPictures()));

                        // postingNumber를 docID로 설정하기
                        Map<String, Object> data = new HashMap<>();
                        data.put("postingNumber", documentReference.getId());
                        data.put("pictures", posting.getPictures());
                        updatePosting(boardName, tagName, documentReference.getId(), data);

//                        // 게시글 정보 추가
//                        PostingInfo postingInfo = new PostingInfo();
//                        documentReference.collection("postingInfo").add(postingInfo);

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

    // 게시물 찾기(docID는 Posting Number)
    private void searchPosting(String postingNum) {
        fsDB.collectionGroup("postings").whereEqualTo("postingNumber", postingNum).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // 게시물 내용 수정 (docID는 Posting Number)_VER1
    public void updatePosting(String boardName, String tagName, String docID, Map<String, Object> newData) {
        CollectionReference colRef = postCollectionRef(boardName, tagName);
        colRef.document(docID).update(newData)
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

    // 게시물 내용 수정 VER2
    public void updatePostings(String postingNum, Map<String, Object> newData) {
        fsDB.collectionGroup("postings").whereEqualTo("postingNumber", postingNum).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference docRef = document.getReference();
                                docRef.update(newData)
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
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    // 게시물 삭제 VER1
    public void delPosting(String boardName, String tagName, Posting posting) {
        StorageManager storage = new StorageManager();

        CollectionReference colRef = postCollectionRef(boardName, tagName);
        colRef.document(posting.getPostingNumber()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Posting has successfully deleted!");

                        // 사진 파일도 지우기
                        int numOfPic = posting.getPictures().size();
                        for (int index = 0; index < numOfPic; index++) {
                            storage.delPostingImage(posting.getPictures().get(index));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.w(TAG, "Error deleting posting", e);
                    }
                });
    }

    // 게시물 삭제 VER2
    public void delPostings(Posting posting) {
        StorageManager storage = new StorageManager();

        fsDB.collectionGroup("postings").whereEqualTo("postingNumber", posting.getPostingNumber()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference docRef = document.getReference();
                                docRef.delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "Posting has successfully updated!");

                                                // 사진 파일도 지우기
                                                int numOfPic = posting.getPictures().size();
                                                for (int index = 0; index < numOfPic; index++) {
                                                    storage.delPostingImage(posting.getPictures().get(index));
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Log.w(TAG, "Error updating posting", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // 게시물 정보 수정
    final private int INCRESE = 0;
    final private int DECRESE = 1;

    final public void updatePostInfo(String postingNumber, String field, int inORdecrese) {
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

        DocumentReference colRef = fsDB.collection("posting").document(postingNumber);
        colRef.update(data)
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

    // 태그로 게시물 불러오기
    public Query getPostWithTag(String tagName) {
        return fsDB.collectionGroup("postings").whereEqualTo("tag", tagName).orderBy("postedTime", Query.Direction.DESCENDING);
    }

    // postingNumber로 게시물 불러오기
    public Query getPostWithNum(String postingNum) {
        return fsDB.collectionGroup("postings").whereEqualTo("postingNumber", postingNum);
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
    public void addComment(String postingNumber, Comment comment) {
        CollectionReference colRef = fsDB.collection("posting").document(postingNumber).collection("comment");
        colRef.add(comment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // postingNumber를 docID로 설정하기
                        Map<String, Object> id = new HashMap<>();
                        id.put("docID", documentReference.getId());
                        updateComment(postingNumber, documentReference.getId(), id);

                        updatePostInfo(postingNumber, "numberOfComment", INCRESE);

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

    // 게시물 댓글 수정 (postingNumber은 게시물 번호)
    public void updateComment(String postingNumber, String docID, Map<String, Object> newData) {
        CollectionReference colRef = fsDB.collection("posting").document(postingNumber).collection("comment");
        colRef.document(docID).update(newData)
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

    // 게시물 삭제 (postingNumber은 게시물 번호, docID는 comment의 댓글 번호)
    public void delComment(String postingNumber, String docID) {
        CollectionReference colRef = fsDB.collection("posting").document(postingNumber).collection("comment");
        colRef.document(docID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        updatePostInfo(postingNumber, "numberOfComment", DECRESE);
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


    /* ---------------------- Chatting용 DB ---------------------- */
    // 채팅방 추가
    public void addChatRoom(ChattingInfo chattingInfo) {
        fsDB.collection("chattingRoom").add(chattingInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Map<String, Object> id = new HashMap<>();
                        id.put("roomNumber", documentReference.getId());
                        updateChatRoom(documentReference.getId(), id);
                        addMessage(documentReference.getId(), new ChattingMsg(chattingInfo.getParticipant().get(0) + "님과 " + chattingInfo.getParticipant().get(1) + "님이 입장하셨습니다."));

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


}