package com.example.cineview.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.Activities.DetailFilm;
import com.example.cineview.R;
import com.example.cineview.models.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {

    private Context context;
    private List<MovieItem> movieList;

    public ImageSliderAdapter(Context context, List<MovieItem> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem movie = movieList.get(position);
        Glide.with(holder.imageView.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailFilm.class);
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
            v.getContext().startActivity(intent);

            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

