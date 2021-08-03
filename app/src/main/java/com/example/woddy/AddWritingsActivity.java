package com.example.woddy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.woddy.DB.FirebaseManager;
import com.example.woddy.Entity.MemberInfo;
import com.example.woddy.Entity.Posting;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddWritingsActivity extends AppCompatActivity {
    private File tempFile;
    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;

    Button cancelBtn, finishBtn, addImageBtn;
    EditText titleTV, plotTV;
    TextView testTV;
    Spinner spinner;
    static int writing_index = 1;
    int image_index = 1;
    String tag = "";
    String pictures = "";

    FirebaseStorage storage;
    StorageReference storageRef;
    DatabaseReference db;
    FirebaseManager firebaseManager;
    FirebaseManager firestoreManager;

    InputMethodManager imm;

    String[] tags = {"태그1", "태그2", "태그3", "태그4", "태그5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        setContentView(R.layout.add_writings_main);

        db = FirebaseDatabase.getInstance().getReference();
        firebaseManager = new FirebaseManager(db);
        firestoreManager = new FirebaseManager();

        spinner = (Spinner) findViewById(R.id.spinner);
        addImageBtn = (Button) findViewById(R.id.addImages);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        titleTV = (EditText) findViewById(R.id.titleTextView);
        plotTV = (EditText) findViewById(R.id.plotTextView);
        testTV = (TextView) findViewById(R.id.testTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = tags[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tag = tags[0];
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleTV.getText().toString().isEmpty() && !plotTV.getText().toString().isEmpty()) {
                    final String title = titleTV.getText().toString();
                    final String content = plotTV.getText().toString();
                    Posting post = new Posting("P000000" + writing_index, tag, "writer", title, content, pictures);
                    firestoreManager.PostingUpload(post);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    writing_index++;
                } else {
                    Toast.makeText(getApplicationContext(), "제목과 내용 모두를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

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
            Uri photoUri = data.getData();
            StorageReference storageRef = storage.getReference();
            StorageReference attachedImage = storageRef.child("user/myPostings/postNumbers/postNum" + writing_index + "/image/imageNum" + image_index + ".png");
            UploadTask uploadTask = attachedImage.putFile(photoUri);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                setImage(bitmap);
                pictures = photoUri.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getApplicationContext(), "사진이 정상적으로 업로드되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "사진이 정상적으로 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
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
        imageLayout.addView(iv);

        tempFile = null;
        image_index = image_index + 1;
    }

}


