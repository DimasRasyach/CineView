package com.example.cineview.models;

import java.util.List;

public class FavoriteMoviesResponse {
    private boolean success;
    private String message;
    private List<MovieItem> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MovieItem> getData() {
        return data;
    }

    public void setData(List<MovieItem> data) {
        this.data = data;
    }
}
