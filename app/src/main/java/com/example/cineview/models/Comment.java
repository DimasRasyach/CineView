package com.example.cineview.models;

public class Comment {
    private String username;
    private String commentText;
    private int profileImageResId;

    public Comment(String username, String commentText, int profileImageResId) {
        this.username = username;
        this.commentText = commentText;
        this.profileImageResId = profileImageResId;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }
}

