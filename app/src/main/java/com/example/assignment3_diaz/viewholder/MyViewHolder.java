package com.example.assignment3_diaz.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3_diaz.R;
import com.example.assignment3_diaz.listener.MovieClickListener;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public final TextView title;
    public final TextView description;
    public final ImageView poster;
    public final Button detailButton;
    public final Button favouritesButton;

    public MyViewHolder(@NonNull View itemView, MovieClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_title);
        description = itemView.findViewById(R.id.movie_year);
        poster = itemView.findViewById(R.id.movie_poster);
        detailButton = itemView.findViewById(R.id.detail_button);
        favouritesButton = itemView.findViewById(R.id.favouriteButton);


        detailButton.setOnClickListener(v -> {
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                listener.onClick(v, getAdapterPosition());
            }
        });

        favouritesButton.setOnClickListener(v -> {
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                listener.onClick(v, getAdapterPosition());
            }
        });
    }
}