package com.example.woddy.MyPage;

import static android.content.ContentValues.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

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
import com.example.woddy.PostingListActivity;
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

    Button userWritings, userLiked, userScraped;

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

        userWritings = view.findViewById(R.id.myPage_userWritings);
        userLiked = view.findViewById(R.id.myPage_likedWritings);
        userScraped = view.findViewById(R.id.myPage_scrappedWritings);

        setAccount = view.findViewById(R.id.myPage_setAccount);
        delAccount = view.findViewById(R.id.myPage_deleteAccount);
        logOut = view.findViewById(R.id.myPage_logout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String firebaseUserUID = firebaseUser.getUid();
        Log.d(TAG, firebaseUserUID);

        //?????? ???????????? ???????????? ????????? ???????????? + ????????? ??????????????? ????????? ?????? ?????? ?????? ??????(?????????, ??????, ??????)
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

                    FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage ???????????? ??????
                    StorageReference storageRef = storage.getReference(tmp_imguri); // ???????????? ????????? ???????????? ???????????? ?????????

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
        userWritings.setOnClickListener(this);
        userLiked.setOnClickListener(this);
        userScraped.setOnClickListener(this);
        setAccount.setOnClickListener(this);
        delAccount.setOnClickListener(this);
        logOut.setOnClickListener(this);

        return view;
    }

    //?????? ?????? ????????? ?????????
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myPage_btn_update_myProfile: //????????? ??????
                Intent intent = new Intent(getContext(), UpdateProfile.class);
                intent.putExtra("nickname", tmp_nick);
                intent.putExtra("local", tmp_local);
                intent.putExtra("imguri", tmp_imguri);
                startActivity(intent);
                break;

            case R.id.myPage_userWritings:
                intent = new Intent(getContext(), PostingListActivity.class);
                intent.putExtra("Activity", 1);
                startActivity(intent);
                break;

            case R.id.myPage_likedWritings:
                intent = new Intent(getContext(), PostingListActivity.class);
                intent.putExtra("Activity", 2);
                startActivity(intent);
                break;

            case R.id.myPage_scrappedWritings:
                intent = new Intent(getContext(), PostingListActivity.class);
                intent.putExtra("Activity", 3);
                startActivity(intent);
                break;

            case R.id.myPage_setAccount: //?????? ??????
                Intent setAccountIntent = new Intent(getContext(), SetAccountActivity.class);
                startActivity(setAccountIntent);
                break;
            case R.id.myPage_deleteAccount: //????????????
                Intent delAccountIntent = new Intent(getContext(), DelAccountActivity.class);
                startActivity(delAccountIntent);
                break;
            case R.id.myPage_logout: //????????????
                showDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getContext(), "?????????????????????.", Toast.LENGTH_LONG).show();
            return;
        }

//        if (requestCode == UPDATE) {
//            local.setText(data.getStringExtra("local"));
//            userImage.setImageURI(Uri.parse(data.getStringExtra("userImage")));
//        }
    }

    //???????????? ????????????
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("????????????")
                .setMessage("???????????? ???????????????????")
                .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getContext(), "???????????? ???????????????", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LogInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}