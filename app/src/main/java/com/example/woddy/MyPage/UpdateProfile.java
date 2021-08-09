package com.example.woddy.MyPage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woddy.BaseActivity;
import com.example.woddy.DB.StorageManager;
import com.example.woddy.R;

public class UpdateProfile extends BaseActivity {
    ImageView profileImage;
    Button btnUpdate;

    private int GALLEY_CODE = 100;
    private String imageUrl = "";

    StorageManager sManager = new StorageManager();

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setTitle("마이페이지");

        profileImage = findViewById(R.id.updateProfile_userImage);
        btnUpdate = findViewById(R.id.updateProfile_btn_complete);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로컬 사진첩으로 이동
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLEY_CODE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("profileImage", profileImage.toString());

                Toast.makeText(getBaseContext(), profileImage.getResources().toString(), Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_content, MyPageFragment.newInstance(bundle)).commit();

                finish();   /* ---- 화면 비정상적으로 종료되는 현상 수정 필요 ---- */
            }
        });

    }

    /* --- 앨범애서 사진을 골라 사용자 이미지로 설정하기 --- */
    // 사진의 절대경로 구하지
    private String getRealPathFromUri (Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
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
        if (requestCode == GALLEY_CODE && resultCode == RESULT_OK) {
            try {
                imageUrl = getRealPathFromUri(data.getData());

                // DBtest부분
                sManager.setProfileImage("user4", imageUrl);    // ****** 위치 여기서 이동해야함 : 작성 완료 눌렀을 때 추가되도록! ******

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .circleCrop()
                        .into(profileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == GALLEY_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "갤러리 접근 권한을 허용되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    /* ---- 권한 동의 부분 (수정 필요, 뭔가 이상함..) -- */
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