package com.example.travelinfro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private DatabaseReference mDatabase;

    EditText mEdtEmail,mEdtPwd;
    Button mBtnLogIn,mBtnSignIn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mBtnLogIn = findViewById(R.id.sign_in_btn_login);
        mBtnSignIn = findViewById(R.id.sign_in_btn_sign_in);

        mEdtEmail = findViewById(R.id.sign_in_edt_email);
        mEdtPwd = findViewById(R.id.sign_in_edt_pw);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEdtEmail.getText().toString().trim();
                String pwd = mEdtPwd.getText().toString().trim();
                logIn(email,pwd);
            }
        });
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEdtEmail.getText().toString().trim();
                String pwd = mEdtPwd.getText().toString().trim();
                Log.e("mBtnSignIn","email: "+email);
                Log.e("mBtnSignIn","pwd: "+pwd);
                createAccount(email,pwd);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (firebaseAuth.getCurrentUser() != null) {
            onAuthSuccess(firebaseAuth.getCurrentUser());
        }
    }

    private void logIn(String email,String pwd){

        if(!isValidEmail(email)){
            Log.e("logIn","email is not valid");
            Toast.makeText(SignInActivity.this,"이메일 형식에 맞지 않습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidPasswd(pwd)){
            Log.e("logIn","pwd is not valid");
            Toast.makeText(SignInActivity.this,"비밀번호 형식에 맞지 않습니다(6자리 이상, 한글 미포함.",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(TAG,"login email: "+email);
        Log.e(TAG,"login pwd: "+pwd);

        firebaseAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            onAuthSuccess(Objects.requireNonNull(task.getResult().getUser()));
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignInActivity.this,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void createAccount(String email,String pwd){

        Log.e("createAccount","email: "+email);
        Log.e("createAccount","pwd: "+pwd);

        if(!isValidEmail(email)){
            Log.e("createAccount","email is not valid");
            Toast.makeText(SignInActivity.this,"이메일 형식에 맞지 않습니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidPasswd(pwd)){
            Log.e("createAccount","pwd is not valid");
            Toast.makeText(SignInActivity.this,"비밀번호 형식에 맞지 않습니다(6~16자리, 한글 미포함.",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this,"회원 가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                            onAuthSuccess(Objects.requireNonNull(task.getResult().getUser()));
                        }else{
                            Log.e("Error: ",""+task.getException());

                            Toast.makeText(SignInActivity.this,"회원 가입에 실패했습니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user){
        String username = usernameFromEmail(Objects.requireNonNull(user.getEmail()));
        writeNewUser(user.getUid(),username,user.getEmail());
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean isValidPasswd(String target) {
        //비밀번호 유효성 검사 (6자리~16자리, 한글 미포함)

        Pattern p = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$");
        Matcher m = p.matcher(target);

        if (m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            return true;
        }else{
            return false;
        }
    }

    private boolean isValidEmail(String target) {
        //이메일 유효성 검사
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
