package com.example.cineview.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.Activities.DetailFilm;
import com.example.cineview.R;
import com.example.cineview.models.MovieItem;
import com.example.cineview.models.TopRatingModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopRatingAdapter extends RecyclerView.Adapter<TopRatingAdapter.ViewHolder> {

    private Context context;
    private List<MovieItem> movieList;
    public TopRatingAdapter(Context context, List<MovieItem> movieList) {
        this.movieList = movieList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageProfile;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.imageProfile);
            title = itemView.findViewById(R.id.title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_rating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem movie = movieList.get(position);
        Glide.with(holder.imageProfile.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageProfile);

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
        });
    }
    //        TopRatingModel item = movieList.get(position);
//        holder.imageProfile.setImageResource(item.getImageResId());
//        holder.title.setText(item.getTitle());

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}

