package com.example.cineview.Activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.example.cineview.R;
import com.google.android.material.button.MaterialButton;

public class detailfilm extends AppCompatActivity {

    private boolean isFavorited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailfilm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailfilm), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        NestedScrollView scrollView = findViewById(R.id.detailfilm);
        View ratingSection = findViewById(R.id.ratingSection);
        View commentSection = findViewById(R.id.commentInputSection);

        MaterialButton ratingButton = findViewById(R.id.ratingButton);
        MaterialButton commentButton = findViewById(R.id.commentButton);

        ratingButton.setOnClickListener(v -> {
            scrollView.post(() -> scrollView.smoothScrollTo(0, ratingSection.getTop()));
        });

        commentButton.setOnClickListener(v -> {
            scrollView.post(() -> scrollView.smoothScrollTo(0, commentSection.getTop()));
        });

        MaterialButton favoriteButton = findViewById(R.id.favoriteButton);

        favoriteButton.setOnClickListener(v -> {
            isFavorited = !isFavorited;

            if (isFavorited) {
                favoriteButton.setIconResource(R.drawable.heartfill);
                favoriteButton.setIconTint(null);
            } else {
                favoriteButton.setIconResource(R.drawable.heart);
                favoriteButton.setIconTint(ColorStateList.valueOf(Color.WHITE));
            }
        });

        // Inisialisasi bintang
        ImageView[] stars = new ImageView[5];
        stars[0] = findViewById(R.id.star1);
        stars[1] = findViewById(R.id.star2);
        stars[2] = findViewById(R.id.star3);
        stars[3] = findViewById(R.id.star4);
        stars[4] = findViewById(R.id.star5);

        int[] selectedRating = {0};

        for (int i = 0; i < stars.length; i++) {
            int finalI = i;
            stars[i].setOnClickListener(v -> {
                for (int j = 0; j < stars.length; j++) {
                    if (j <= finalI) {
                        stars[j].setImageTintList(null);
                    } else {
                        stars[j].setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(detailfilm.this, android.R.color.white)));
                    }
                }
                selectedRating[0] = finalI + 1;
            });
        }

        Button submitRatingButton = findViewById(R.id.submitRatingButton);
        submitRatingButton.setOnClickListener(v -> {
            if (selectedRating[0] > 0) {
                Toast.makeText(detailfilm.this, "Rating " + selectedRating[0] + " berhasil dikirim!", Toast.LENGTH_SHORT).show();
                // TODO: Kirim rating ke server/database

                // Reset bintang ke kondisi awal (semua putih)
                for (ImageView star : stars) {
                    star.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(detailfilm.this, android.R.color.white)));
                }
                selectedRating[0] = 0;
            } else {
                Toast.makeText(detailfilm.this, "Silakan pilih rating terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        });



        EditText commentEditText = findViewById(R.id.commentEditText);
        Button sendCommentButton = findViewById(R.id.sendCommentButton);

        sendCommentButton.setOnClickListener(v -> {
            String comment = commentEditText.getText().toString().trim();

            if (!comment.isEmpty()) {
                Toast.makeText(detailfilm.this, "Komentar berhasil dikirim!", Toast.LENGTH_SHORT).show();
                commentEditText.setText("");  // Clear isi komentar setelah dikirim
                // TODO: Kirim komentar ke server/database
            } else {
                Toast.makeText(detailfilm.this, "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}