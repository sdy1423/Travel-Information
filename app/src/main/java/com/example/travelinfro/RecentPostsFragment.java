package com.example.travelinfro;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class RecentPostsFragment extends PostListFragment {

    public RecentPostsFragment(String board) {
        super(board);
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Log.e("RecentPostsFragment","getQuary");
        Query recentPostsQuery = databaseReference.child(board).child("posts")
                .limitToFirst(100);
        return recentPostsQuery;
    }


}
