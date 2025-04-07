package com.example.assignment3_diaz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3_diaz.viewholder.MyViewHolder;
import com.example.assignment3_diaz.R;
import com.example.assignment3_diaz.listener.MovieClickListener;
import com.example.assignment3_diaz.model.Movie;
import com.example.assignment3_diaz.views.MovieDetailsActivity;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final ArrayList<Movie> movies;
    private final Context context;
    private MovieClickListener clickListener;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setClickListener(MovieClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_layout, parent, false);
        return new MyViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // bind movie data to views
        Movie movie = movies.get(position);
        holder.title.setText(movie.getMovieName());
        holder.description.setText(movie.getYear());

        // load image
        String posterUrl = movie.getPosterUrl();
        if (posterUrl != null && !posterUrl.equals("N/A")) {
            new Thread(() -> {
                try {
                    java.net.URL url = new java.net.URL(posterUrl);
                    android.graphics.Bitmap bmp = android.graphics.BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    holder.poster.post(() -> holder.poster.setImageBitmap(bmp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        holder.detailButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
            intent.putExtra("movieTitle", movie.getMovieName());
            intent.putExtra("movieYear", movie.getYear());
            intent.putExtra("movieCast", movie.getActors() != null ? movie.getActors() : "Cast information not available");
            intent.putExtra("movieDesc", movie.getPlot() != null ? movie.getPlot() : "Description not available");
            intent.putExtra("posterUrl", movie.getPosterUrl());
            intent.putExtra("imdbId", movie.getImdbId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
