package com.example.woddy.MyPage;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.User;
import com.example.woddy.Login.LogInActivity;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MyPageFragment extends Fragment implements View.OnClickListener {
    TextView nickName;
    TextView local;
    ImageView userImage;
    Button updateProfile;

    Button setAccount;
    Button delAccount;
    Button logOut;

    FirebaseUser firebaseUser;
    FirestoreManager fsManager;
    private int UPDATE = 200;
    static final String TAG = "MypageFragment";

    String nick_str;
    String tmp_nick;
    String tmp_local;
    String tmp_imguri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        nickName = view.findViewById(R.id.myPage_userNick);
        local = view.findViewById(R.id.myPage_userLocal);
        userImage = view.findViewById(R.id.myPage_userImage);
        updateProfile = view.findViewById(R.id.myPage_btn_update_myProfile);

        setAccount = view.findViewById(R.id.myPage_setAccount);
        delAccount = view.findViewById(R.id.myPage_deleteAccount);
        logOut = view.findViewById(R.id.myPage_logout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String firebaseUserUID = firebaseUser.getUid();
        Log.d(TAG, firebaseUserUID);

        //현재 로그인한 사용자의 닉네임 가져오기 + 가져온 닉네임으로 화면에 띄울 유저 정보 세팅(닉네임, 지역, 사진)
        fsManager = new FirestoreManager();
        fsManager.findUserWithUid(firebaseUserUID).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.getResult() != null) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    tmp_nick = documentSnapshot.getString("nickname");
                    tmp_local = documentSnapshot.getString("local");
                    tmp_imguri = documentSnapshot.getString("userImage");

                    nickName.setText(tmp_nick);
                    local.setText(tmp_local);

                    FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                    StorageReference storageRef = storage.getReference(tmp_imguri); // 스토리지 공간을 참조해서 이미지를 가져옴

                    storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Glide.with(view)
                                        .load(task.getResult())
                                        .circleCrop()
                                        .into(userImage);
                            } else {
                                Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                            }
                        }
                    });
                }
            }
        });
        fsManager.findUserWithUid(firebaseUserUID).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                nick_str = (String) task.getResult().get("nickname");
                Log.d(TAG, nick_str);

            }
        });

        updateProfile.setOnClickListener(this);
        setAccount.setOnClickListener(this);
        delAccount.setOnClickListener(this);
        logOut.setOnClickListener(this);

        return view;
    }

    //버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myPage_btn_update_myProfile: //프로필 수정
                Intent intent = new Intent(getContext(), UpdateProfile.class);
                intent.putExtra("nickname", tmp_nick);
                intent.putExtra("local", tmp_local);
                intent.putExtra("imguri", tmp_imguri);
                startActivity(intent);
                break;
            case R.id.myPage_setAccount: //계정 관리
                Intent setAccountIntent = new Intent(getContext(), SetAccountActivity.class);
                startActivity(setAccountIntent);
                break;
            case R.id.myPage_deleteAccount: //회원탈퇴
                Intent delAccountIntent = new Intent(getContext(), DelAccountActivity.class);
                startActivity(delAccountIntent);
                break;
            case R.id.myPage_logout: //로그아웃
                showDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getContext(), "취소되었습니다.", Toast.LENGTH_LONG).show();
            return;
        }

//        if (requestCode == UPDATE) {
//            local.setText(data.getStringExtra("local"));
//            userImage.setImageURI(Uri.parse(data.getStringExtra("userImage")));
//        }
    }

    //로그아웃 대화상자
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LogInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}