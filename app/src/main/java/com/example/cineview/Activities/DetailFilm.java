package com.example.cineview.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.R;
import com.example.cineview.adapter.GenreAdapter;

import java.util.ArrayList;

public class DetailFilm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailfilm);

        TextView titleText = findViewById(R.id.titleText);
        TextView descText = findViewById(R.id.descriptionText);
        TextView yearText = findViewById(R.id.yearText);
        TextView categoryText = findViewById(R.id.categoryText);
        TextView ratingText = findViewById(R.id.ratingText);
        ImageView posterImage = findViewById(R.id.imageposter);
        RecyclerView genreRecyclerView = findViewById(R.id.genreRecyclerView);

// Ambil data dari Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int releaseYear = intent.getIntExtra("releaseYear", 0);
        String category = intent.getStringExtra("category");
        double averageRating = intent.getDoubleExtra("averageRating", 0.0);
        String posterUrl = intent.getStringExtra("posterUrl");

        ArrayList<String> genres = getIntent().getStringArrayListExtra("movieGenres");
        if (genres == null) {
            genres = new ArrayList<>();
        }

// Tampilkan ke UI
        titleText.setText(title);
        descText.setText(description);
        yearText.setText("Tahun: " + releaseYear);
        categoryText.setText("Kategori: " + category);
        ratingText.setText("Rating: " + averageRating + " / 5");
        Glide.with(this).load(posterUrl).into(posterImage);

        // RecyclerView Genre
        GenreAdapter genreAdapter = new GenreAdapter(genres);
        genreRecyclerView.setAdapter(genreAdapter);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}