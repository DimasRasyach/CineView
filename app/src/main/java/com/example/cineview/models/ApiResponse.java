package com.example.cineview.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;
    private String error;
    @SerializedName("userId")
    private String userId;

    @SerializedName("username")
    private String username;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    public String getToken () {
        return token;
    }
    public String getError() {
        return error;
    }
    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
}
