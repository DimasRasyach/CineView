package com.example.cineview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private List<String> genres;

    public GenreAdapter(List<String> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre_chip, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.genreText.setText(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreText;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreText = itemView.findViewById(R.id.genreText);
        }
    }
}

