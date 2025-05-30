package com.example.cineview.models;

public class TopRatingModel {
    private int imageResId;
    private String title;

    public TopRatingModel(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }
}

