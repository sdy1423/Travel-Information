package com.example.travelinfro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;

public class NewPostActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "문자를 입력하세요";

    EditText mEdtTitle,mEdtBody;
    FloatingActionButton mFabSubmitPost;
    private DatabaseReference mDatabase;
    int boardNum = 0;
    String board = "board";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Intent intent = new Intent(this.getIntent());
        boardNum = intent.getIntExtra("boardNum",0);
        Log.e(TAG,"get board num"+boardNum);
        board +=String.valueOf(boardNum);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEdtTitle = findViewById(R.id.new_post_edt_title);
        mEdtBody = findViewById(R.id.new_post_edt_body);

        mFabSubmitPost = findViewById(R.id.new_post_submit);
        mFabSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

    }

    private void submitPost(){
        final String title = mEdtTitle.getText().toString();
        final String body = mEdtBody.getText().toString();

        if(TextUtils.isEmpty(title)){
            mEdtTitle.setError(REQUIRED);
            return;
        }

        if(TextUtils.isEmpty(body)){
            mEdtBody.setError(REQUIRED);
            return;
        }
        Log.e(TAG,"submitPost title: "+title);
        Log.e(TAG,"submitPost body: "+body);

        setEditingEnabled(false);
        Toast.makeText(this, "포스팅 중입니다.", Toast.LENGTH_SHORT).show();

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();;
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user == null) {
                            //유저 조회 실패
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: 존재하지 않는 유저입니다.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 유저 조회 성공, 글쓰기 시작
                            writeNewPost(userId, user.username, title, body);
                        }
                        setEditingEnabled(true);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "getUser:onCancelled", error.toException());
                        setEditingEnabled(true);
                    }
                }
        );
    }

    private void setEditingEnabled(boolean enabled) {
        mEdtTitle.setEnabled(enabled);
        mEdtBody.setEnabled(enabled);
        if (enabled) {
            mFabSubmitPost.show();
        } else {
            mFabSubmitPost.hide();
        }
    }

    private void writeNewPost(String userId,String userName,String title,String body){

        String key = mDatabase.child(board).child("posts").push().getKey();
        Post post = new Post(userId,userName,title,body);
        Map<String ,Object> postValues = post.toMap();
        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+board+"/posts/"+key,postValues);
        childUpdates.put("/"+board+"/user-posts/"+userId+"/"+key,postValues);
        mDatabase.updateChildren(childUpdates);
    }
}
