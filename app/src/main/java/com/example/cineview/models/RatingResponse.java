package com.example.cineview.models;

import java.util.List;

public class RatingResponse {
    private String movieId;
    private String title;
    private double averageRating;
    private int totalRatings;
    private List<RatingItem> ratings;

    // Getter & Setter
    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public int getTotalRatings() { return totalRatings; }
    public void setTotalRatings(int totalRatings) { this.totalRatings = totalRatings; }

    public List<RatingItem> getRatings() { return ratings; }
    public void setRatings(List<RatingItem> ratings) { this.ratings = ratings; }

    // === Inner class RatingItem ===
    public static class RatingItem {
        private User user;
        private int rating;

        public User getUser() { return user; }
        public void setUser(User user) { this.user = user; }

        public int getRating() { return rating; }
        public void setRating(int rating) { this.rating = rating; }
    }

    // === Inner class User ===
    public static class User {
        private String _id;
        private String email;

        public String getId() { return _id; }
        public void setId(String _id) { this._id = _id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
