package com.example.cineview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.Activities.detailfilm;
import com.example.cineview.R;
import com.example.cineview.models.TopRatingModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopRatingAdapter extends RecyclerView.Adapter<TopRatingAdapter.ViewHolder> {

    private List<TopRatingModel> dataList;
    private Context context;

    public TopRatingAdapter(Context context, List<TopRatingModel> dataList) {
        this.context = context;
        this.dataList = dataList;
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
        TopRatingModel item = dataList.get(position);
        holder.imageProfile.setImageResource(item.getImageResId());
        holder.title.setText(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, detailfilm.class);
            intent.putExtra("MOVIE_TITLE", item.getTitle()); 
            context.startActivity(intent);

            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
