package com.example.cineview.models;

public class RatingRequest {
    private String userId;
    private int rating;

    public RatingRequest(String userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }
}
