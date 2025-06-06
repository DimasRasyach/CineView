package com.example.cineview.models;

public class RatingRequest {
    private int rating;

    public RatingRequest(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
