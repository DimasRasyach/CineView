package com.example.cineview.models;

import android.media.Rating;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieItem {
    @SerializedName("_id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("posterUrl")
    private String posterUrl;
    @SerializedName("genre")
    private List<String> genre;
    @SerializedName("releaseYear")
    private int releaseYear;
    @SerializedName("category")
    private String category;
    @SerializedName("averageRating")
    private double averageRating;

    @SerializedName("ratings")
    private List<RatingData> ratings;

    @SerializedName("comments")
    private List<Comment> comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public List<RatingData> getRatings() {
        return ratings;
    }

}



