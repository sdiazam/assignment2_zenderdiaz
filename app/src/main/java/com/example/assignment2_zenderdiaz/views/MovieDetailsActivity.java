package com.example.assignment2_zenderdiaz.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment2_zenderdiaz.R;

import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView titleView, yearView, castView, descView;
    private ImageView posterView;
    private Button returnButton;
    private MovieDetailsViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // bind views
        titleView = findViewById(R.id.detail_title);
        yearView = findViewById(R.id.detail_year);
        castView = findViewById(R.id.detail_cast);
        descView = findViewById(R.id.detail_desc);
        posterView = findViewById(R.id.detail_poster);
        returnButton = findViewById(R.id.return_button);

        // get intent extras
        String imdbId = getIntent().getStringExtra("imdbId");
        String movieTitle = getIntent().getStringExtra("movieTitle");
        String movieYear = getIntent().getStringExtra("movieYear");
        String posterUrl = getIntent().getStringExtra("posterUrl");

        // set initial text values
        titleView.setText(movieTitle);
        yearView.setText(movieYear);

        // load poster image in background
        if (posterUrl != null && !posterUrl.equals("N/A")) {
            new Thread(() -> {
                try {
                    URL url = new URL(posterUrl);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    posterView.post(() -> posterView.setImageBitmap(bmp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // setup viewmodel
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        // observe movie detail
        viewModel.getMovie().observe(this, movie -> {
            if (movie != null) {
                castView.setText(movie.getActors() != null ? "Starring: " + movie.getActors() : "Cast not available");
                descView.setText(movie.getPlot() != null ? movie.getPlot() : "Plot not available");
            }
        });


        // observe error
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                castView.setText("Cast not available");
                descView.setText("Plot not available");
            }
        });

        // fetch movie by id
        viewModel.fetchMovieById(imdbId);

        // back button
        returnButton.setOnClickListener(v -> finish());
    }
}
