package com.example.cineview.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.Activities.DetailFilm;
import com.example.cineview.R;
import com.example.cineview.models.MovieItem;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieItem> movieList;
    private OnFavoriteClickListener favoriteClickListener;

    public interface OnFavoriteClickListener {
        void onAddFavoriteClicked(String movieId);
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteClickListener = listener;
    }

    public MovieAdapter(Context context, List<MovieItem> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_result, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieItem movie = movieList.get(position);

        if (movie != null) {
            // Set gambar poster
            Glide.with(context)
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.placeholder) // Gambar sementara saat loading
                    .error(R.drawable.error_image)      // Gambar jika gagal load
                    .into(holder.imagePoster);

            // Set judul
            holder.textTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");

            // Set sinopsis
            holder.textSynopsis.setText(movie.getDescription() != null ? movie.getDescription() : "");

            // Set rating
            if (movie.getAverageRating() > 0) {
                holder.textRating.setText(String.valueOf(movie.getAverageRating()));
            } else {
                holder.textRating.setText("-");
            }
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailFilm.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("description", movie.getDescription());
                intent.putExtra("releaseYear", movie.getReleaseYear());
                intent.putExtra("category", movie.getCategory());
                intent.putExtra("averageRating", movie.getAverageRating());
                intent.putExtra("posterUrl", movie.getPosterUrl());
                intent.putExtra("movie_id", movie.getId());
                Log.d("Adapter", "Movie ID sent: " + movie.getId());

                ArrayList<String> genreList = new ArrayList<>(movie.getGenre());
                intent.putStringArrayListExtra("genre", genreList);
                context.startActivity(intent);

                if (context instanceof android.app.Activity) {
                    ((android.app.Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imagePoster;
        TextView textTitle, textSynopsis, textRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSynopsis = itemView.findViewById(R.id.textSynopsis);
            textRating = itemView.findViewById(R.id.textRating);
        }
    }
}
