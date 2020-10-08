package com.example.travelinfro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment(String board) {
        super(board);
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child(board).child("user-posts").child(getUid());
    }

}
