package com.example.travelinfro;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String uid;
    private String author;
    private String title;
    private String body;
    public int starCount = 0;
    public Map<String,Boolean> stars = new HashMap<>();

    public Post(){

    }

    public Post(String uid,String author,String title,String body){
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }
}
