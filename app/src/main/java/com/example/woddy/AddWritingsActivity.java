package com.example.woddy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddWritingsActivity extends AppCompatActivity {
    private File tempFile;
    private Boolean isPermission = true;

    private final int GET_GALLERY_IMAGE = 200;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private ImageView imageView;
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Button cancelBtn, finishBtn, addImageBtn;
    EditText titleTV, plotTV;
    TextView testTV;
    int writing_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_writings_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.addWriting);
        addImageBtn = (Button) findViewById(R.id.addImages);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        titleTV = (EditText) findViewById(R.id.titleTextView);
        plotTV = (EditText) findViewById(R.id.plotTextView);
        testTV = (TextView) findViewById(R.id.testTextView);

        myDBHelper = new myDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onUpgrade(sqlDB, 1, 2);
        sqlDB.close();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")

            @Override
            public void onClick(View v) {
                if (titleTV.getText().toString().isEmpty() == false && plotTV.getText().toString().isEmpty() == false) {
                    sqlDB = myDBHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO writings VALUES ('" + titleTV.getText().toString() + "' , '" + plotTV.getText().toString() + "');");
                    Toast.makeText(getApplicationContext(), "insert finished", Toast.LENGTH_SHORT).show();

                    sqlDB = myDBHelper.getReadableDatabase();
                    Cursor cursor;
                    cursor = sqlDB.rawQuery("SELECT * FROM writings;", null);

                    Toast.makeText(getApplicationContext(), "2 finished", Toast.LENGTH_SHORT).show();

                    String strTitle = "";
                    String strPlot = "";

                    while (cursor.moveToNext()) {
                        strTitle += cursor.getString(0);
                        strPlot += cursor.getString(1);
                    }
                    testTV.setText(strTitle + '\n' + strPlot);
                    cursor.close();
                    sqlDB.close();

                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                    writing_index++;
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
                if (isPermission) goToAlbum();
                else
                    Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });
    }


    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "writingsDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE writings ( writing_title TEXT , writing_plot TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS writings");
            onCreate(db);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

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
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                tempFile = new File(cursor.getString(column_index));
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            setImage();
        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

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

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
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

    private void setImage() {

        LinearLayout imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        ImageView iv = new ImageView(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
        lp.setMargins(20, 0, 20, 0);

        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setLayoutParams(lp);
        iv.setAdjustViewBounds(true);
        iv.setImageBitmap(originalBm);
        iv.setMaxWidth(250);
        imageLayout.addView(iv);

        tempFile = null;

    }

}


