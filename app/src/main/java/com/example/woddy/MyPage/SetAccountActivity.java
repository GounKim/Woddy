package com.example.woddy.MyPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class SetAccountActivity extends BaseActivity {
    TextView emailTextView;
    Button pwChangeButton;
    ImageView toolbarLogoImage;
    FirestoreManager fsManager;
    String email;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_account);
        setMyTitle("계정 관리");

        toolbarLogoImage = findViewById(R.id.toolbar_logo);
        toolbarLogoImage.setVisibility(View.GONE);
        emailTextView = findViewById(R.id.set_account_email_text_view);
        pwChangeButton = findViewById(R.id.pw_change_button);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fsManager = new FirestoreManager();
        fsManager.findUserWithUid(uid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.getResult() != null) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    email = documentSnapshot.getString("userID");
                    emailTextView.setText(email);
                }
            }
        });

        pwChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetAccountActivity.this, ChangePwActivity.class);
                startActivity(intent);
            }
        });
    }
}