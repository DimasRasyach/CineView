package com.example.cineview.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.R;
import com.example.cineview.adapter.GenreAdapter;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.RatingData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilm extends AppCompatActivity {

    private ImageView[] stars = new ImageView[5];
    private int selectedRating = 0;
    private String movieId;
    private TextView currentRatingText;

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
        currentRatingText = findViewById(R.id.currentRatingText);
        


        // Intent data
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int year = intent.getIntExtra("releaseYear", 0);
        String category = intent.getStringExtra("category");
        double rating = intent.getDoubleExtra("averageRating", 0.0);
        String poster = intent.getStringExtra("posterUrl");
        ArrayList<String> genres = intent.getStringArrayListExtra("genre");
        if (genres == null) genres = new ArrayList<>();

        // Set UI
        titleText.setText(title);
        descText.setText(description);
        yearText.setText("Tahun: " + year);
        categoryText.setText("Kategori: " + category);
        ratingText.setText("Rating: " + rating + " / 5");
        Glide.with(this).load(poster).into(posterImage);


        movieId = getIntent().getStringExtra("movie_id");
        Log.d("DetailFilm", "Movie ID: " + movieId);

        GenreAdapter genreAdapter = new GenreAdapter(genres);
        genreRecyclerView.setAdapter(genreAdapter);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setupStars();

        findViewById(R.id.submitRatingButton).setOnClickListener(v -> {
            if (selectedRating == 0) {
                Toast.makeText(this, "Pilih rating terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }
            // Ambil userId dari SharedPreferences
            SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
            String token = prefs.getString("auth_token", null);
            String userId = prefs.getString("user_id", null);

            if (token == null || userId == null) {
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            String authHeader = "Bearer " + token;

            RatingData ratingData = new RatingData(userId, selectedRating);
            ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
            Call<Void> call = apiService.postRating(authHeader, movieId, ratingData);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(DetailFilm.this, "Rating terkirim!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailFilm.this, "Rating gagal terkirim: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(DetailFilm.this, "Gagal mengirim rating", Toast.LENGTH_SHORT).show();
                    Log.e("DetailFilm", "Error saat mengirim rating", t);
                }
            });
        });
    }

    private void setupStars() {
        stars[0] = findViewById(R.id.star1);
        stars[1] = findViewById(R.id.star2);
        stars[2] = findViewById(R.id.star3);
        stars[3] = findViewById(R.id.star4);
        stars[4] = findViewById(R.id.star5);

        for (int i = 0; i < stars.length; i++) {
            final int rating = i + 1;
            stars[i].setOnClickListener(v -> {
                selectedRating = rating;
                updateStarUI(rating);
                currentRatingText.setText(String.valueOf(rating));
            });
        }
    }

    private void updateStarUI(int rating) {
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setImageTintList(null);
            } else {
                stars[i].setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, android.R.color.white)
                ));
            }
        }
    }
}
