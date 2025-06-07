package com.example.cineview.models;

import com.google.gson.annotations.SerializedName;

public class MovieIdRequest {
    @SerializedName("movieId")
    private String movieId;

    public MovieIdRequest(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
