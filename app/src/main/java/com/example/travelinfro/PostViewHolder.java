package com.example.travelinfro;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        bodyView = itemView.findViewById(R.id.postBody);
    }
    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.getTitle());
        authorView.setText(post.getAuthor());
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.getBody());
        starView.setOnClickListener(starClickListener);

        Log.e("postviewholder","post"+post.getTitle());
    }

}
