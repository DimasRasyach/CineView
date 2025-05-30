package com.example.cineview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.R;
import com.example.cineview.models.MovieItem;

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
        holder.imagePoster.setImageResource(movie.getImageResId());
        holder.textTitle.setText(movie.getTitle());
        holder.textRatingValue.setText(movie.getRating());
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

