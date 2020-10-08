package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;


public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PostDetailActivity";
    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private CommentAdapter mAdapter;

    String board = "board";

    RecyclerView mRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = new Intent(this.getIntent());
        board = intent.getStringExtra("board");
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        Log.e(TAG,"board: "+board);
        Log.e(TAG,"postkey: "+mPostKey);


        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child(board).child("posts").child(mPostKey);
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child(board).child("post-comments").child(mPostKey);

        mRecycler = findViewById(R.id.post_detail_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                Log.e(TAG,"post.getAuthor "+post.getAuthor());

                TextView postAuthor,postTitle,postBody;
                postAuthor = findViewById(R.id.postAuthor);
                postTitle = findViewById(R.id.postTitle);
                postBody = findViewById(R.id.postBody);

                postAuthor.setText(Objects.requireNonNull(post).getAuthor()); //글쓴이 (이메일에서 추출함)
                postTitle.setText(post.getTitle()); //제목
                postBody.setText(post.getBody()); //내용

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 실패 했을 때
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(PostDetailActivity.this, "게시물을 로드하는 데 실패했습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mPostReference.addValueEventListener(postListener);

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;

        // Listen for comments
        mAdapter = new CommentAdapter(this, mCommentsReference);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i==R.id.post_detail_btn_comment_post) {
            postComment();
        }
    }

    private void postComment() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();;

        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // 유저 정보를 가져 온다.
                        User user = dataSnapshot.getValue(User.class);
                        String authorName = user.username;

                        EditText edtComment = findViewById(R.id.post_detail_edt_comment);
                        String commentText = edtComment.getText().toString();
                        Comment comment = new Comment(uid, authorName, commentText);

                        mCommentsReference.push().setValue(comment);

                        edtComment.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }
        mAdapter.cleanupListener();
    }

}
