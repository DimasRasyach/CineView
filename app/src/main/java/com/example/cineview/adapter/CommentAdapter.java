package com.example.cineview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineview.R;
import com.example.cineview.models.Comment;
import com.example.cineview.models.UserModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment currentComment = commentList.get(position);
        holder.commentTextView.setText(currentComment.getText());
        UserModel user = currentComment.getUser();
        if (user!= null && user.getUsername() != null) {
            holder.usernameTextView.setText(user.getUsername());
        } else {
            holder.usernameTextView.setText("Username");
        }
        // Load image from URL using Glide or Picasso
        Glide.with(context)
                .load(currentComment.getProfileimageUrl())
                .placeholder(R.drawable.foto) // default image if null
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView usernameTextView;
        TextView commentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.commentProfileImage);
            usernameTextView = itemView.findViewById(R.id.commentUsername);
            commentTextView = itemView.findViewById(R.id.commentText);
        }
    }
}
