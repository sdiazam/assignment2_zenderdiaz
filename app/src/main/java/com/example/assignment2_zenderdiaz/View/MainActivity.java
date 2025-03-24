package com.example.assignment2_zenderdiaz.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2_zenderdiaz.MyAdapter;
import com.example.assignment2_zenderdiaz.databinding.ActivityMainBinding;
import com.example.assignment2_zenderdiaz.listener.MovieClickListener;
import com.example.assignment2_zenderdiaz.model.MovieModel;
import com.example.assignment2_zenderdiaz.model.MovieResponse;
import com.example.assignment2_zenderdiaz.utils.APIClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MovieClickListener {
    private final ArrayList<MovieModel> movieList = new ArrayList<>();
    private ActivityMainBinding binding;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(getApplicationContext(), movieList);
        binding.recyclerView.setAdapter(myAdapter);
        myAdapter.setClickListener(this);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.searchBar.getText().toString().trim();
                if (!title.isEmpty()) {
                    fetchMovies(title);
                } else {
                    Toast.makeText(MainActivity.this, "Enter a movie title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fetchMovies("marvel");
    }

    private void fetchMovies(String title) {
        binding.progressBar.setVisibility(View.VISIBLE);

        APIClient.get(title, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    final String jsonData = response.body().string();
                    final Gson gson = new Gson();
                    final MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);
                    runOnUiThread(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        if (movieResponse != null && movieResponse.getSearch() != null) {
                            movieList.clear();
                            movieList.addAll(movieResponse.getSearch());
                            myAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "No movies found.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "API Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v, int pos) {
        Toast.makeText(this, "You Choose: " + movieList.get(pos).getMovieName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}