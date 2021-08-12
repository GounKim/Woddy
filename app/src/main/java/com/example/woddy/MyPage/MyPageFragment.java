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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MyPageFragment extends Fragment implements View.OnClickListener {
    TextView nickName;
    TextView introduce;
    TextView local;
    ImageView userImage;
    Button updateProfile;

    Button setAccount;
    Button delAccount;
    Button logOut;

    private int UPDATE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        nickName = view.findViewById(R.id.myPage_userNick);
        introduce = view.findViewById(R.id.myPage_userIntroduce);
        local = view.findViewById(R.id.myPage_userLocal);
        userImage = view.findViewById(R.id.myPage_userImage);
        updateProfile = view.findViewById(R.id.myPage_btn_update_myProfile);

        setAccount = view.findViewById(R.id.myPage_setAccount);
        delAccount = view.findViewById(R.id.myPage_deleteAccount);
        logOut = view.findViewById(R.id.myPage_logout);

        FirestoreManager manager = new FirestoreManager();
        manager.findUser("user1")
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                            User user = document.toObjects(User.class).get(0);

                            nickName.setText(user.getNickName());
                            introduce.setText(user.getIntroduce());
                            local.setText(user.getLocal());

                            FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                            StorageReference storageRef = storage.getReference(user.getUserImage()); // 스토리지 공간을 참조해서 이미지를 가져옴

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

                        } else {
                            Log.d(TAG, "Finding user has failed.", task.getException());
                        }
                    }
                });


        updateProfile.setOnClickListener(this);
        setAccount.setOnClickListener(this);
        delAccount.setOnClickListener(this);
        logOut.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myPage_btn_update_myProfile:
                Intent intent = new Intent(getContext(), UpdateProfile.class);
                startActivityForResult(intent, UPDATE);
                break;
            case R.id.myPage_setAccount:
                Intent setAccountIntent = new Intent(getContext(), SetAccountActivity.class);
                startActivity(setAccountIntent);
                break;
            case R.id.myPage_deleteAccount:
                Intent delAccountIntent = new Intent(getContext(), DelAccountActivity.class);
                startActivity(delAccountIntent);
                break;
            case R.id.myPage_logout:
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

        if (requestCode == UPDATE) {
            introduce.setText(data.getStringExtra("introduce"));
            local.setText(data.getStringExtra("local"));
            userImage.setImageURI(Uri.parse(data.getStringExtra("userImage")));
        }
    }

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