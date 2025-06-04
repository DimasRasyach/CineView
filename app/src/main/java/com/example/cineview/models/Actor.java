package com.example.cineview.models;

public class Actor {
    private String name;
    private int imageResId; // jika dari drawable

    public Actor(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
}
