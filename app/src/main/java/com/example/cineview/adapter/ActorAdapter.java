package com.example.cineview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.R;
import com.example.cineview.models.Actor;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {
    private List<Actor> actorList;

    public ActorAdapter(List<Actor> actorList) {
        this.actorList = actorList;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actorList.get(position);
        holder.actorName.setText(actor.getName());
        holder.imageActor.setImageResource(actor.getImageResId()); // atau pakai Glide jika gambar dari URL
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    static class ActorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageActor;
         TextView actorName;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageActor = itemView.findViewById(R.id.imageActor);
            actorName = itemView.findViewById(R.id.actorName);
        }
    }
}
