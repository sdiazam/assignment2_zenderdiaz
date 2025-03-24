package com.example.assignment2_zenderdiaz.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2_zenderdiaz.R;


public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView titleView = findViewById(R.id.detail_title);
        TextView yearView = findViewById(R.id.detail_year);
        TextView castView = findViewById(R.id.detail_cast);
        TextView descView = findViewById(R.id.detail_desc);
        ImageView posterView = findViewById(R.id.detail_poster);
        Button returnButton = findViewById(R.id.return_button);

        String movieTitle = getIntent().getStringExtra("movieTitle");
        String movieYear = getIntent().getStringExtra("movieYear");
        String movieCast = getIntent().getStringExtra("movieCast");
        String movieDesc = getIntent().getStringExtra("movieDesc");
        String posterUrl = getIntent().getStringExtra("posterUrl");

        titleView.setText(movieTitle);
        yearView.setText(movieYear);
        castView.setText(movieCast);
        descView.setText(movieDesc);


        returnButton.setOnClickListener(v -> finish());
    }
}