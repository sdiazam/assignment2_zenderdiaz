package com.example.assignment2_zenderdiaz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_zenderdiaz.listener.MovieClickListener;
import com.example.assignment2_zenderdiaz.model.MovieModel;
import com.example.assignment2_zenderdiaz.View.MovieDetailsActivity;

import java.io.InputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<com.example.assignment2_zenderdiaz.MyViewHolder> {

    private final ArrayList<MovieModel> movies;
    private final Context context;
    private MovieClickListener clickListener;

    public MyAdapter(Context context, ArrayList<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setClickListener(MovieClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_layout, parent, false);
        return new MyViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movie = movies.get(position);
        holder.title.setText(movie.getMovieName());
        holder.description.setText(movie.getYear());


        holder.detailButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
            intent.putExtra("movieTitle", movie.getMovieName());
            intent.putExtra("movieYear", movie.getYear());
            intent.putExtra("movieCast", "Cast information not available");
            intent.putExtra("movieDesc", "Description not available");
            intent.putExtra("posterUrl", movie.getPosterUrl());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}