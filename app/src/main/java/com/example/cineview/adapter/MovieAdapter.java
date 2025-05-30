package com.example.cineview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.R;
import com.example.cineview.models.MovieItem;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieItem> movieList;

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
            if (movie.getImageResId() != 0) {
                holder.imagePoster.setImageResource(movie.getImageResId());
            }

            // Set judul
            holder.textTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");

            // Set sinopsis
            holder.textSynopsis.setText(movie.getSynopsis() != null ? movie.getSynopsis() : "");

            // Set rating
            if (movie.getRating() != null) {
                holder.textRating.setText(String.valueOf(movie.getRating()));
            } else {
                holder.textRating.setText("-");
            }
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
