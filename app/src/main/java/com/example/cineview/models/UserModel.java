package com.example.cineview.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("username")

    private String username;
    private String email;
    private List<String> favoriteMovies;
    @SerializedName("password")
    private String password;

    public UserModel(String id, String username, String email, List<String> favoriteMovies) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.favoriteMovies = favoriteMovies;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public UserModel(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
