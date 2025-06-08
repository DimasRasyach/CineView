package com.example.cineview.models;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("_id")
    private String id;
    @SerializedName("userId")
    private UserModel user;
    @SerializedName("text")
    private String text;
    private String createdAt;
    @SerializedName("username")
    private String username;
    private String profileimageUrl;

    public Comment(String id, UserModel user, String text, String createdAt, String username, String profileimageUrl) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
        this.username = username;
        this.profileimageUrl = profileimageUrl;
    }

    public Comment(String text, UserModel user) {
        this.text = text;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileimageUrl() {
        return profileimageUrl;
    }

    public void setProfileimageUrl(String profileimageUrl) {
        this.profileimageUrl = profileimageUrl;
    }
}

