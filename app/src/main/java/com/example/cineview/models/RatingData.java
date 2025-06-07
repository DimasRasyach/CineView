package com.example.cineview.models;

public class RatingData {
    private UserModel user;
    private int rating;

    public UserModel getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public static class User {
        private String _id;

        public String getId() {
            return _id;
        }
    }
}
