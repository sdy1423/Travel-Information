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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

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
    private void logIn(String email,String pwd){

        if(!isValidEmail(email)){
            Log.e("logIn","email is not valid");
            Toast.makeText(SignInActivity.this,"이메일 형식에 맞지 않습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isValidPasswd(pwd)){
            Log.e("logIn","pwd is not valid");
            Toast.makeText(SignInActivity.this,"비밀번호 형식에 맞지 않습니다(6자리 이상, 한글 미포함.",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignInActivity.this,"로그인에 실패했습니다.",Toast.LENGTH_SHORT);
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
        if(isValidPasswd(pwd)){
            Log.e("createAccount","pwd is not valid");
            Toast.makeText(SignInActivity.this,"비밀번호 형식에 맞지 않습니다(6자리 이상, 한글 미포함.",Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this,"회원 가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("Error: ",""+task.getException());

                            Toast.makeText(SignInActivity.this,"회원 가입에 실패했습니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean isValidPasswd(String target) {
        //비밀번호 유효성 검사 (6자리 이상, 한글 미포함)
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");
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
