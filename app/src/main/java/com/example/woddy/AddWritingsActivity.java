package com.example.woddy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddWritingsActivity extends AppCompatActivity {
    private File tempFile;
    private Boolean isPermission = true;

    private static final int Gallery_Code = 10;
    private static final int PICK_FROM_ALBUM = 1;
//    private static final int PICK_FROM_CAMERA = 2;

    Button cancelBtn, finishBtn, addImageBtn;
    EditText titleTV, plotTV;
    TextView testTV;
    ImageView imageView;
    static int writing_index = 0;
    static int image_index = 0;

    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        setContentView(R.layout.add_writings_main);

        addImageBtn = (Button) findViewById(R.id.addImages);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        titleTV = (EditText) findViewById(R.id.titleTextView);
        plotTV = (EditText) findViewById(R.id.plotTextView);
        testTV = (TextView) findViewById(R.id.testTextView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference title = database.getReference("user/writings/" + writing_index + "/title");
        DatabaseReference plot = database.getReference("user/writings/" + writing_index + "/plot");
        StorageReference attachedImagesRef = storageRef.child("user/writings/" + writing_index + "/images/" + image_index);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                writing_index = writing_index + 1;

                if (!titleTV.getText().toString().isEmpty() && !plotTV.getText().toString().isEmpty()) {
                    title.setValue(titleTV.getText().toString());
                    plot.setValue(plotTV.getText().toString());

                    title.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String strTitle = dataSnapshot.getValue().toString();
                            testTV.setText("title: " + strTitle + ", ");
                            Log.d("Database", "Value is: " + strTitle);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w("Database", "Failed to read value.", error.toException());
                        }
                    });

                    plot.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String strPlot = dataSnapshot.getValue().toString();
                            testTV.setText((testTV.getText() + "plot : " + strPlot));
                            Log.d("Database", "Value is: " + strPlot);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("Database", "Failed to read value.", error.toException());
                        }
                    });

                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddWritingsActivity.this);

                    builder.setTitle("입력 오류").setMessage("입력되지 않은 부분이 있습니다.");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
            StorageReference attachedImage = storageRef.child("user/writings" + writing_index + "/image" + image_index + ".png");
            UploadTask uploadTask = attachedImage.putFile(photoUri);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                setImage(bitmap);
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

    private void setImage(Bitmap img) {
        image_index = image_index + 1;
        LinearLayout imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        ImageView iv = new ImageView(this);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
        lp.setMargins(20, 0, 20, 0);

        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setLayoutParams(lp);
        iv.setAdjustViewBounds(true);
        iv.setImageBitmap(img);
        iv.setMaxWidth(250);
        imageLayout.addView(iv);

        tempFile = null;
    }

}


