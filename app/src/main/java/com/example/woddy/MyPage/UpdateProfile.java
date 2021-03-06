package com.example.woddy.MyPage;

import static com.example.woddy.Home.HomeFragment.USER_UID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.SQLiteManager;
import com.example.woddy.DB.StorageManager;
import com.example.woddy.Login.SignUpActivity;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends BaseActivity {
    ImageView profileImageView;
    EditText newNickEditText;
    EditText newLocalEditText;
    TextView nickCheckTextView;
    Button changeProfileImgButton;
    Button changeNickButton;
    Button changeLocalButton;
    ImageView toolbarLogoImage;

    private int GALLERY_CODE = 100;
    private Boolean isPermission = true;
    private String imageUrl = "";
    private String uid;
    final static String TAG = "UpdateProfile";

    String tmp_nick;
    String tmp_local;
    String tmp_imguri;

    StorageManager sManager = new StorageManager();
    FirestoreManager fsManager = new FirestoreManager();

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected boolean useBackButton() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setMyTitle("????????? ??????");

        //?????????
        profileImageView = findViewById(R.id.update_profile_image_view);
        newNickEditText = findViewById(R.id.update_nick_edit_text);
        newLocalEditText = findViewById(R.id.update_local_edit_text);
        nickCheckTextView = findViewById(R.id.update_nick_check_text_view);
        changeProfileImgButton = findViewById(R.id.update_profile_image_button);
        changeNickButton = findViewById(R.id.update_nick_button);
        changeLocalButton = findViewById(R.id.update_local_button);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        toolbarLogoImage = findViewById(R.id.toolbar_logo);

        //????????? ???????????? ?????? ??????
        Intent intent = getIntent();
        tmp_nick = intent.getStringExtra("nickname");
        tmp_local = intent.getStringExtra("local");
        tmp_imguri = intent.getStringExtra("imguri");
        toolbarLogoImage.setVisibility(View.GONE);
        //1) ????????? ??????
        FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage ???????????? ??????
        StorageReference storageRef = storage.getReference(tmp_imguri); // ???????????? ????????? ???????????? ???????????? ?????????
        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(getApplicationContext())
                            .load(task.getResult())
                            .circleCrop()
                            .into(profileImageView);
                } else {
                    Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                }
            }
        });
        //2) ?????? ??? ?????????
        newNickEditText.setText(tmp_nick);
        //3) ?????? ??? ??????
        newLocalEditText.setText(tmp_local);


        //?????? ??????
        //1) ????????? ?????? ??????
        changeProfileImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tedPermission();
                if (isPermission) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, GALLERY_CODE);
                }
            }
        });

        //2) ????????? ??????
        changeNickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNick();
            }
        });

        //3) ?????? ??????
        changeLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newLocal = newLocalEditText.getText().toString();
                Log.d(TAG, newLocal);
                String[] tmpStrArr = newLocal.split(" ");
                if (tmpStrArr.length != 3) {
                    Toast.makeText(UpdateProfile.this, "????????? ?????? ??????????????????.", Toast.LENGTH_LONG).show();
                    return;
                }
                String finalCity = tmpStrArr[0];
                String finalGu = tmpStrArr[1];
                String finalDong = tmpStrArr[2];
                String finalLocal = finalCity + " " + finalGu + " " + finalDong;
                Map<String, Object> map = new HashMap<>();
                map.put("city", finalCity);
                map.put("gu", finalGu);
                map.put("dong", finalDong);
                map.put("local", finalLocal);
                fsManager.updateProfile(uid, map);
                tmp_local = newLocal;
                newLocalEditText.setText(tmp_local);
                Toast.makeText(UpdateProfile.this, "?????? ?????? ??????!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* --- ???????????? ????????? ?????? ????????? ???????????? ???????????? --- */
    // ????????? ???????????? ?????????
    private String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String url = cursor.getString(columnIndex);
        cursor.close();
        return url;
    }

    // ???????????? ????????? ???????????? ?????? ??????(url)??? ????????? ??????????????? ??????
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "?????????????????????.", Toast.LENGTH_LONG).show();
            return;
        }

        if (requestCode == GALLERY_CODE) {
            try {
                imageUrl = getRealPathFromUri(data.getData());

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .circleCrop()
                        .into(profileImageView);
                sManager.setProfileImage(imageUrl);
                Toast.makeText(UpdateProfile.this, "????????? ?????? ?????? ??????!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /* ---- ?????? ?????? ?????? -- */
    private void tedPermission() {
        // ?????? ?????? ??????
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // ?????? ?????? ??????
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // ?????? ?????? ??????
                isPermission = false;
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    public void checkNick() {
        String nick_str = newNickEditText.getText().toString();
        FirestoreManager fsManager = new FirestoreManager();
        fsManager.findNickname(nick_str)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() == 0) {
                            nickCheckTextView.setVisibility(View.VISIBLE);
                            nickCheckTextView.setTextColor(Color.GRAY);
                            nickCheckTextView.setText("?????? ????????? ??????????????????.");
                            String newNick = newNickEditText.getText().toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("nickname", newNick);
                            fsManager.updateProfile(uid, map);
                            tmp_nick = newNick;
                            newNickEditText.setText(tmp_nick);
                            Toast.makeText(UpdateProfile.this, "????????? ?????? ??????!", Toast.LENGTH_SHORT).show();
                        } else {
                            nickCheckTextView.setVisibility(View.VISIBLE);
                            nickCheckTextView.setTextColor(Color.rgb(255, 105, 105));
                            nickCheckTextView.setText("????????? ??????????????????.");
                        }
                    }
                });
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_GRANTED) { // ????????? ???????????? ???
            // ?????? ??????????????? ??????
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLEY_CODE);
        } else {
            Toast.makeText(this, "????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }


    private int PERMISSION_GRANTED = 2;
    private int PERMISSION_DENIED = 1;
    public void checkSelfPermission() {
        String temp = "";

        // ?????? ?????? ?????? ??????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        // ?????? ?????? ?????? ??????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (TextUtils.isEmpty(temp) == false) { // ?????? ???????????? ????????? ???
            // ?????? ??????
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), PERMISSION_DENIED);
        } else {
            // ?????? ?????? ??????
           startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), PERMISSION_GRANTED);
        }


    }

 */
}