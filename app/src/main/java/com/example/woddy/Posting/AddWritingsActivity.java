package com.example.woddy.Posting;

import static android.content.ContentValues.TAG;
import static com.example.woddy.Home.HomeFragment.USER_UID;

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
import com.example.woddy.DB.SQLiteManager;
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
import java.util.Arrays;
import java.util.Date;

public class AddWritingsActivity extends BaseActivity {
    private File tempFile;
    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;

    ImageView addImageBtn;
    EditText titleTV, plotTV;
    TextView tvBoarName, tvTagName;

    int image_index = 1;

    ArrayList<String> uriList = new ArrayList<>();

    FirebaseStorage storage;
    StorageReference storageRef;
    FirestoreManager firestoreManager;
    StorageManager sManager;
    SQLiteManager sqlmanager;

    String boardName;
    String tagName;
    ImageView toolbarImage;

    String[] imgNeedTag = {"????????????", "???", "????????????", "DIY", "???????????????"};
    InputMethodManager imm;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_writings);
        Intent intent = getIntent();
        boardName = intent.getStringExtra("boardName");
        tagName = intent.getStringExtra("tagName");

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        firestoreManager = new FirestoreManager();
        sManager = new StorageManager();
        sqlmanager = new SQLiteManager(this);

        addImageBtn = (ImageView) findViewById(R.id.addPhotoImage);
        titleTV = (EditText) findViewById(R.id.titleTextView);
        plotTV = (EditText) findViewById(R.id.plotTextView);
        tvBoarName = (TextView) findViewById(R.id.add_writing_board_name);
        tvTagName = (TextView) findViewById(R.id.add_writing_tag_name);
        toolbarImage = (ImageView) findViewById(R.id.toolbar_logo);

        // ?????? ??????
        setSupportActionBar(getmToolbar());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_clear_24);
        actionBar.setDisplayShowCustomEnabled(true);    // ????????????????????????
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);  // ???????????? ??????
        toolbarImage.setVisibility(View.GONE);
        setMyTitle("??? ??????");

        // ????????? & ?????? ?????? ???????????? ?????????
        tvBoarName.setText(boardName + "?????????");
        tvTagName.setText("#" + tagName);


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
                // ????????? ????????? ?????? ??????????????? ??????
                if (!titleTV.getText().toString().isEmpty() && !plotTV.getText().toString().isEmpty()) {
                    // ???????????? ????????? ????????? ???????????? ?????? ??????
                    if (Arrays.asList(imgNeedTag).contains(tagName)) {
                        if (uriList.size() == 0) {  // ???????????? ???????????? ????????? ?????? ?????????
                            Toast.makeText(getApplicationContext(), "????????? ?????? ?????? ???????????????.", Toast.LENGTH_LONG).show();
                            break;
                        } else {    // ???????????? ???????????? ?????????
                            addNewPosting();
                        }
                    } else {    //???????????? ????????? ????????? ????????? ?????????
                        addNewPosting();
                    }
                } else {// ????????? ????????? ?????? ???????????? ????????? ???????????? ??????
                    Toast.makeText(getApplicationContext(), "????????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                    break;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNewPosting() {
        Posting post;
        final String title = titleTV.getText().toString();
        final String content = plotTV.getText().toString();
        String uid = USER_UID;
        if (uriList == null) {
            post = new Posting(sqlmanager.getUserNick(), title, content, new Date(), uid);
        } else {
            post = new Posting(sqlmanager.getUserNick(), title, content, uriList, new Date(), uid);
        }
        firestoreManager.addPosting(boardName, tagName, post);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    // ????????? ?????? ???????????? ??????
    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(titleTV.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(plotTV.getWindowToken(), 0);
    }

    // ?????????????????? ?????? ???????????????
    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // ???????????? ????????? ???????????? ?????? ??????
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "?????????????????????.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "??????????????? ???????????? ???????????????.", Toast.LENGTH_LONG).show();
            } else {
                // ???????????? ????????? ????????? ??????
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
                    // ???????????? ????????? ????????? ??????
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
                    // ???????????? 10?????? ????????? ?????? ??????
                    Toast.makeText(getApplicationContext(), "????????? 10????????? ?????? ???????????????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

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

    // ?????? ???????????? ????????? ????????? ????????????
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

        iv.setLongClickable(true);
        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getApplicationContext());
                dlg.setMessage("?????????????????????????");
                dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iv.setVisibility(View.GONE);
                    }
                });
                dlg.setPositiveButton("??????", null);
                return false;
            }
        });

        tempFile = null;
        image_index = image_index + 1;
    }

}


