package com.example.cineview.models;

public class MovieItem {
    private int imageResId;
    private String title;
    private String rating;
    private String synopsis;

    public MovieItem(int imageResId, String title, String rating, String synopsis) {
        this.imageResId = imageResId;
        this.title = title;
        this.rating = rating;
        this.synopsis = synopsis;
    }

    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getRating() { return rating; }
    public String getSynopsis() { return synopsis; }

    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
    public void setTitle(String title) { this.title = title; }
    public void setRating(String rating) { this.rating = rating; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
}
