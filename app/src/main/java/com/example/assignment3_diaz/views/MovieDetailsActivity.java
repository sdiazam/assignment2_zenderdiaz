package com.example.assignment3_diaz.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3_diaz.databinding.ActivityMovieDetailsBinding;

import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get intent from first view
        String imdbId = getIntent().getStringExtra("imdbId");
        String movieTitle = getIntent().getStringExtra("movieTitle");
        String movieYear = getIntent().getStringExtra("movieYear");
        String posterUrl = getIntent().getStringExtra("posterUrl");

        binding.detailTitle.setText(movieTitle);
        binding.detailYear.setText(movieYear);

        //poster loading
        if (posterUrl != null && !posterUrl.equals("N/A")) {
            new Thread(() -> {
                try {
                    URL url = new URL(posterUrl);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    runOnUiThread(() -> binding.detailPoster.setImageBitmap(bmp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        // observe movie detail
        viewModel.getMovie().observe(this, movie -> {
            if (movie != null) {
                binding.detailCast.setText(movie.getActors() != null ? "starring: " + movie.getActors() : "cast not available");
                binding.detailDesc.setText(movie.getPlot() != null ? movie.getPlot() : "plot not available");
            }
        });

        // observe error
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                binding.detailCast.setText("cast not available");
                binding.detailDesc.setText("plot not available");
            }
        });
        viewModel.fetchMovieById(imdbId);

        // back button
        binding.returnButton.setOnClickListener(v -> finish());
    }
}