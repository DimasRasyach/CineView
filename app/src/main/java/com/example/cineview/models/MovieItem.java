package com.example.cineview.models;

public class MovieItem {
    private int imageResId;
    private String title;
    private String rating;

    public MovieItem(int imageResId, String title, String rating) {
        this.imageResId = imageResId;
        this.title = title;
        this.rating = rating;
    }

    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getRating() { return rating; }
}
