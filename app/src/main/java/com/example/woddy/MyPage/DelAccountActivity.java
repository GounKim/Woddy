package com.example.woddy.MyPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Login.LogInActivity;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class DelAccountActivity extends BaseActivity {
    static final String TAG = "DelAccountAct";
    CheckBox agreeCheckBox;
    Button backButton;
    Button delAccountButton;
    FirebaseUser firebaseUser;
    FirestoreManager fsManager;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_account);
        setMyTitle("회원탈퇴");

        agreeCheckBox = findViewById(R.id.agree_check_box);
        backButton = findViewById(R.id.back_button);
        delAccountButton = findViewById(R.id.del_account_button);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fsManager = new FirestoreManager(getApplicationContext());

        String uid = firebaseUser.getUid().toString();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreeCheckBox.isChecked()) {
                    firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account delete completed.");
                                fsManager.deleteUserProfile(uid);
                                //fsManager.deleteUser();
                                //sqlite에서도 삭제

                                Toast.makeText(DelAccountActivity.this, "탈퇴가 완료되었습니다", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DelAccountActivity.this, LogInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(DelAccountActivity.this, "동의에 체크해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}