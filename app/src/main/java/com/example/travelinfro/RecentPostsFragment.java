package com.example.travelinfro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class RecentPostsFragment extends PostListFragment {
    String board = null;

    public RecentPostsFragment(String board) {
        this.board=board;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child(board).child("posts")
                .limitToFirst(100);
        return recentPostsQuery;
    }
}
