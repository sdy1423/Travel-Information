package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.quickstart.database.databinding.ActivityPostDetailBinding;


public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PostDetailActivity";
    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
//    private CommentAdapter mAdapter;
    private ActivityPostDetailBinding binding;

    int boardNum = 0;
    String board = "board";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_post_detail);

        Intent intent = new Intent(this.getIntent());
        boardNum = intent.getIntExtra("boardNum",0);
        board +=String.valueOf(boardNum);

        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(mPostKey);
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-comments").child(mPostKey);


    }

    @Override
    public void onClick(View v) {

    }
}
