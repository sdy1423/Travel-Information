package com.example.travelinfro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class MyTopPostsFragment extends PostListFragment {

    String board = null;

    public MyTopPostsFragment(String board) {
        this.board = board;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child(board).child("user-posts").child(myUserId)
                .orderByChild("starCount");
        return myTopPostsQuery;    }
}
