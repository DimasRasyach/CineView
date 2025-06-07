package com.example.cineview.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.cineview.adapter.CommentAdapter;
import com.example.cineview.adapter.GenreAdapter;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.Comment;
import com.example.cineview.models.CommentRequest;
import com.example.cineview.models.CommentResponse;
import com.example.cineview.models.RatingData;
import com.example.cineview.models.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilm extends AppCompatActivity {

    private ImageView[] stars = new ImageView[5];
    private int selectedRating = 0;
    private String movieId;
    private TextView currentRatingText;
    private EditText commentEditText;
    private Button sendCommentButton;
    private RecyclerView commentRecyclerView;

    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

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



        commentEditText = findViewById(R.id.commentEditText);
        sendCommentButton = findViewById(R.id.sendCommentButton);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);


        // Adapter kosong
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(commentAdapter);

        // Ambil komentar dari server
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        apiService.getCommentsByMovieId(movieId).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommentResponse commentResponse = response.body();
                    List<Comment> fetchedComments = commentResponse.getComments();
                    if (fetchedComments != null && !fetchedComments.isEmpty()) {
                        commentList.clear();
                        commentList.addAll(fetchedComments);
                        commentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Toast.makeText(DetailFilm.this, "Network Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DetailFilm", "onFailure", t);
            }
        });



        commentEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // sembunyikan keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });

        sendCommentButton.setOnClickListener(v -> {
            String commentText = commentEditText.getText().toString().trim();
            if (commentText.isEmpty()) {
                Toast.makeText(this, "Komentar tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
            String token = prefs.getString("auth_token", null);
            String userId = prefs.getString("user_id", null);
            String username = prefs.getString("username", "Pengguna");  // Baca ulang di sini

            if (token == null || userId == null) {
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            String authHeader = "Bearer " + token;
            CommentRequest commentRequest = new CommentRequest(userId, commentText);

           apiService.postComment(authHeader, movieId, commentRequest).enqueue(new Callback<Void>() {
               @Override
               public void onResponse(Call<Void> call, Response<Void> response) {
                   if (response.isSuccessful()) {
                       UserModel currentUser = new UserModel(userId, username);
                       Comment comment = new Comment(commentText, currentUser);
                       commentList.add(0, comment);
                       commentAdapter.notifyItemInserted(0);
                       commentRecyclerView.scrollToPosition(0);
                       commentEditText.setText("");
                       Toast.makeText(DetailFilm.this, "Komentar telah ditambahkan!", Toast.LENGTH_SHORT);
                   } else {
                       Toast.makeText(DetailFilm.this, "Gagal mengirim komentar", Toast.LENGTH_SHORT).show();
                       Log.e("PostComment", "Response gagal: code = " + response.code() + ", message = " + response.message());
                   }
                   }

               @Override
               public void onFailure(Call<Void> call, Throwable t) {
                   Toast.makeText(DetailFilm.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
        });

        findViewById(R.id.submitRatingButton).setOnClickListener(v -> {
            if (selectedRating == 0) {
                Toast.makeText(this, "Pilih rating terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
            String token = prefs.getString("auth_token", null);
            String userId = prefs.getString("user_id", null);
            String username = prefs.getString("username", "Pengguna");  // Baca ulang di sini

            if (token == null || userId == null) {
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            String authHeader = "Bearer " + token;

            RatingData ratingData = new RatingData(userId, selectedRating);
            Call<Void> call = apiService.postRating(authHeader, movieId, ratingData);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(DetailFilm.this, "Rating terkirim!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailFilm.this, "Rating gagal terkirim: " + response.code(), Toast.LENGTH_SHORT).show();
                        String errorBodyString = "";
                        try {
                            // Ambil pesan error dari body response jika ada
                            if (response.errorBody() != null) {
                                errorBodyString = response.errorBody().string();
                            }
                        } catch (Exception e) {
                            Log.e("RatingError", "Error saat membaca error body", e);
                        }

                        Log.e("RatingError", "Gagal mengirim rating. Kode: " + response.code() + ", Pesan: " + response.message() + ", Body: " + errorBodyString);

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
