package com.example.cineview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.R;
import com.example.cineview.models.MovieItem;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<MovieItem> movieList;
    private Context context;

    public FavoriteAdapter(Context context, List<MovieItem> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem movie = movieList.get(position);

        holder.textTitle.setText(movie.getTitle());
        holder.textSynopsis.setText(movie.getSynopsis());
        holder.textRating.setText(movie.getRating());
        holder.imagePoster.setImageResource(movie.getImageResId());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imagePoster;
        TextView textTitle, textSynopsis, textRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSynopsis = itemView.findViewById(R.id.textSynopsis);
            textRating = itemView.findViewById(R.id.textRating);
        }
    }
}

