package com.example.woddy.MyPage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.StorageManager;
import com.example.woddy.Entity.User;
import com.example.woddy.MainActivity;
import com.example.woddy.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*

    닉네임 중복확인 코드
    SingUpActivity.java 안에 checkNickname() 함수 코드 활용하시면 돼요
    이것도 realtime에서 firestore로 바꿔놨고
    whereequalto로 찾는 방식입니다

 */
public class UpdateProfile extends BaseActivity {
    ImageView profileImage;
    //    EditText newNickName;
    EditText newIntrodice;
    EditText newLocal;
    Button btnUpdate;

    private int GALLEY_CODE = 100;
    private Boolean isPermission = true;
    private String imageUrl = "";

    StorageManager sManager = new StorageManager();
    FirestoreManager manager = new FirestoreManager();

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
        setMyTitle("프로필 수정");

        profileImage = findViewById(R.id.updateProfile_userImage);
        btnUpdate = findViewById(R.id.updateProfile_btn_complete);
//        newNickName = findViewById(R.id.updateProfile_edt_nickName);
        newIntrodice = findViewById(R.id.updateProfile_edt_introduce);
        newLocal = findViewById(R.id.updateProfile_edt_local);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로컬 사진첩으로 이동
                tedPermission();
                if (isPermission) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, GALLEY_CODE);
                } else {
                    Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                }

            }
        });

        // 프로필 설정 버튼 클릭시
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Storage에 이미지 저장
                String fileUri = sManager.setProfileImage("user1", imageUrl);

                String local = newLocal.getText().toString();
                String introduce = newIntrodice.getText().toString();

                // DBtest에 수정된 정보 추가
                Map<String, Object> newData = new HashMap<>();
                newData.put("local", local);
                newData.put("introduce", introduce);
                newData.put("userImage", fileUri);
                manager.updateUser("user1", newData);

                // MyPage에 넘길 정보 Intent에 담기
                Intent result = new Intent();
                result.putExtra("local", local);
                result.putExtra("introduce", introduce);
                result.putExtra("userImage", imageUrl);

                setResult(RESULT_OK, result);   // 액티비티가 종료됨을 알림정보 넘기기
                finish();   //액티비티 종료
            }
        });

    }

    /* --- 앨범애서 사진을 골라 사용자 이미지로 설정하기 --- */
    // 사진의 절대경로 구하지
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

    // 앨범에서 사진을 선택하고 해당 경로(url)을 가져와 이미지뷰에 출력
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        if (requestCode == GALLEY_CODE) {
            try {
                imageUrl = getRealPathFromUri(data.getData());

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .circleCrop()
                        .into(profileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /* ---- 권한 동의 부분 -- */
    private void tedPermission() {
        // 접근 권한 요청
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
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

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_GRANTED) { // 권한을 허용했을 때
            // 로컬 사진첩으로 이동
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLEY_CODE);
        } else {
            Toast.makeText(this, "갤러리 접근 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }


    private int PERMISSION_GRANTED = 2;
    private int PERMISSION_DENIED = 1;
    public void checkSelfPermission() {
        String temp = "";

        // 파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        // 파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (TextUtils.isEmpty(temp) == false) { // 권한 허용되지 않았을 시
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), PERMISSION_DENIED);
        } else {
            // 모두 허용 상태
           startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), PERMISSION_GRANTED);
        }


    }

 */
}