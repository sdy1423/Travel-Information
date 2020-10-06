package com.example.travelinfro;

public class Comment {
    private String uid;
    private String author;
    private String text;

    public Comment(){

    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
