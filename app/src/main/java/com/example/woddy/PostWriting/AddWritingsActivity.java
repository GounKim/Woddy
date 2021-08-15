package com.example.woddy.PostWriting;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.StorageManager;
import com.example.woddy.Entity.Posting;
import com.example.woddy.MainActivity;
import com.example.woddy.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddWritingsActivity extends BaseActivity {
    private File tempFile;
    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;

    ImageView addImageBtn;
    EditText titleTV, plotTV;
    TextView boardInfoTV;
    int image_index = 1;

    ArrayList<String> uriList = new ArrayList<>();

    FirebaseStorage storage;
    StorageReference storageRef;
    FirestoreManager firestoreManager;
    StorageManager sManager;

    InputMethodManager imm;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        setContentView(R.layout.activity_add_writings);

        firestoreManager = new FirestoreManager(getApplicationContext());
        sManager = new StorageManager();

        addImageBtn = (ImageView) findViewById(R.id.addPhotoImage);
        titleTV = (EditText) findViewById(R.id.titleTextView);
        plotTV = (EditText) findViewById(R.id.plotTextView);
        boardInfoTV = (TextView) findViewById(R.id.add_writing_board_name);

        // 툴바 설정
        setSupportActionBar(getmToolbar());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_clear_24);
        actionBar.setDisplayShowCustomEnabled(true);    // 커스터마이징하기
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);  // 뒤로가기 버튼
        setMyTitle("글 작성");

        // boardInfoTV에 게시판 / 태그 정보 가져와서 나타내도록 해야 함

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tedPermission();
                if (isPermission) {
                    goToAlbum();
                } else
                    Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_writing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_add_writing:
                if (!titleTV.getText().toString().isEmpty() && !plotTV.getText().toString().isEmpty()) {
                    Posting post;
                    final String title = titleTV.getText().toString();
                    final String content = plotTV.getText().toString();
                    if (uriList == null) {
                        post = new Posting("자랑하기", "user1", title, content, new Date());
                    } else {
                        post = new Posting("자랑하기", "user1", title, content, uriList, new Date());
                    }
                    firestoreManager.addPosting("자유게시판","자랑하기", post);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "제목과 내용 모두를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // 키보드 자동 올라오기 막기
    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(titleTV.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(plotTV.getWindowToken(), 0);
    }

    // 갤러리로부터 사진 가져오기기
    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 이미지를 하나도 선택하지 않은 경우
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            if (data.getClipData() == null) {
                Toast.makeText(this, "다중선택이 불가능한 기기입니다.", Toast.LENGTH_LONG).show();
            } else {
                // 이미지를 하나만 선택한 경우
                if (data.getClipData().getItemCount() == 1 && uriList.size() <= 10) {
                    Uri photoUri = data.getData();
                    uriList.add(photoUri.toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                        setImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (data.getClipData().getItemCount() < 10
                        && data.getClipData().getItemCount() + uriList.size() <= 10) {
                    // 이미지를 여러개 선택한 경우
                    ClipData clipData = data.getClipData();
                    for (int item = 0; item < clipData.getItemCount(); item++) {
                        Uri imageUri = clipData.getItemAt(item).getUri();
                        try {
                            uriList.add(imageUri.toString());
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            setImage(bitmap);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }

                } else {
                    // 이미지가 10장을 초과한 경우 제한
                    Toast.makeText(getApplicationContext(), "사진은 10장까지 추가 가능합니다.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

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

    private File createImageFile() throws IOException {
        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "image" + timeStamp;

        // 이미지가 저장될 폴더 이름 ( image )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/image/");
        if (!storageDir.exists()) storageDir.mkdirs();
        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    // 사진 선택하면 이미지 골라서 보여주기
    private void setImage(Bitmap img) {
        LinearLayout imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        ImageView iv = new ImageView(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
        lp.setMargins(20, 0, 20, 0);

        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setLayoutParams(lp);
        iv.setAdjustViewBounds(true);
        iv.setImageBitmap(img);
        iv.setMaxWidth(250);
        imageLayout.addView(iv, 0);

        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getApplicationContext());
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iv.setVisibility(View.GONE);
                    }
                });
                dlg.setPositiveButton("취소",null);
                return false;
            }
        });

        tempFile = null;
        image_index = image_index + 1;
    }

}


