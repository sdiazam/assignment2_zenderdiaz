package com.example.assignment3_diaz.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment3_diaz.adapter.MovieAdapter;
import com.example.assignment3_diaz.databinding.ActivityMainBinding;
import com.example.assignment3_diaz.listener.MovieClickListener;
import com.example.assignment3_diaz.model.Movie;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MovieClickListener {
    private ActivityMainBinding binding;
    private MovieAdapter movieAdapter;
    private final ArrayList<Movie> movieList = new ArrayList<>();
    private MainViewModel viewModel;
    FirebaseAuth mAuth;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // recycler view
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
        binding.recyclerView.setAdapter(movieAdapter);
        movieAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // observe movie list
        viewModel.getMovies().observe(this, movies -> {
            movieList.clear();
            movieList.addAll(movies);
            movieAdapter.notifyDataSetChanged();
        });

        // observe errors
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        // search button click
        binding.searchButton.setOnClickListener(v -> {
            String title = Objects.requireNonNull(binding.searchBar.getText()).toString().trim();
            if (!title.isEmpty()) {
                viewModel.fetchMovies(title);
            } else {
                Toast.makeText(MainActivity.this, "Enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            // make this go from view to view
            // main activity ->
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println(item.getItemId());

                return true;
            }
        });
    }

    // movie click
    @Override
    public void onClick(View v, int pos) {
        Toast.makeText(this, "You chose: " + movieList.get(pos).getMovieName(), Toast.LENGTH_SHORT).show();
    }


}
