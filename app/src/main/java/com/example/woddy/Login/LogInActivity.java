package com.example.woddy.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Profile;
import com.example.woddy.MainActivity;
import com.example.woddy.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import static com.example.woddy.DB.FirestoreManager.USER_UID;

public class LogInActivity extends AppCompatActivity {
    final String TAG = "LogIn";
    final int RC_SIGN_IN = 9001;
    EditText idEditText;
    EditText pwEditText;
    Button loginButton;
    Button signUpButton;
    Button findPwButton;

    SignInButton googleLoginButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        idEditText = findViewById(R.id.id_edit_text);
        pwEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);
        findPwButton = findViewById(R.id.find_password_button);
        googleLoginButton = findViewById(R.id.google_login_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        setGoogleButtonText(googleLoginButton, "Google 계정으로 회원가입 및 로그인    ");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(this);
//        if (gsa != null) {
//            Toast.makeText(LogInActivity.this, "환영합니다", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
//            startActivity(intent);
//        }

        //이미 로그인 된 경우 Main으로 이동
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpid = idEditText.getText().toString();
                String tmppw = pwEditText.getText().toString();
                if (tmpid.matches("") || tmppw.matches("")) {
                    Toast.makeText(LogInActivity.this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    signIn();
                }
            }
        });

        //find password button click listener
        findPwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, PwResetActivity.class);
                startActivity(intent);
            }
        });

        //google login button click listener
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //google login button setText func
    private void setGoogleButtonText(SignInButton googleLoginButton, String buttonText) {
        int i = 0;
        while (i < googleLoginButton.getChildCount()) {
            View v = googleLoginButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setGravity(Gravity.CENTER);
                return;
            }
            i++;
        }
    }

    void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.d(TAG, result.getStatus().toString());
            }
        }
    }

    void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            assert task != null;
            if (task.isSuccessful()) {
                final String uid = firebaseAuth.getCurrentUser().getUid();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                Uri img = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.user);
                String filename = USER_UID + "_profile.jpg"; // 파일명 생성: 사용자의 NickName_profile.jpg
                String fileUri = "UserProfileImages/" + USER_UID + "/" + filename;
                StorageReference riversRef = storageRef.child(fileUri);
                UploadTask uploadTask = riversRef.putFile(img);
                final String finalUserImage = fileUri;

                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                        Profile profile =
                                new Profile(account.getEmail(), account.getDisplayName(),
                                        "null", "null", "null", "null", finalUserImage);

                        FirestoreManager fsManager = new FirestoreManager();
                        fsManager.addProfile(uid, profile);
                    }
                });

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    void signIn() {
        String email = idEditText.getText().toString().trim();
        String pw = pwEditText.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, "환영합니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LogInActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}