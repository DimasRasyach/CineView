package com.example.cineview.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.Activities.DetailFilm;
import com.example.cineview.R;
import com.example.cineview.models.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieItem> movieList;

    public MovieCardAdapter(Context context, List<MovieItem> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieItem movie = movieList.get(position);

        holder.textTitle.setText(movie.getTitle());
        // contoh load gambar pakai Glide/Picasso
        Glide.with(context).load(movie.getPosterUrl()).into(holder.imagePoster);

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
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView textTitle, textRatingValue;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textRatingValue = itemView.findViewById(R.id.textRatingValue);
        }
    }
}

