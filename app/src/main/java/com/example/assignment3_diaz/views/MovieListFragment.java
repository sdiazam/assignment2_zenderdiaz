package com.example.assignment3_diaz.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment3_diaz.adapter.MovieAdapter;
import com.example.assignment3_diaz.databinding.FragmentMovieListBinding;
import com.example.assignment3_diaz.listener.MovieClickListener;
import com.example.assignment3_diaz.model.Movie;
import com.example.assignment3_diaz.utils.APIClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieListFragment extends Fragment implements MovieClickListener {

    private FragmentMovieListBinding binding;
    private MovieAdapter movieAdapter;
    private final ArrayList<Movie> movieList = new ArrayList<>();
    private MainViewModel viewModel;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collection = db.collection("Users");
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        movieAdapter = new MovieAdapter(requireContext(), movieList);
        binding.recyclerView.setAdapter(movieAdapter);
        movieAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            movieList.clear();
            for (Movie movie : movies) {
                fetchMovieDetails(movie); // Fetch detailed info for each movie
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovieDetails(Movie movie) {
        String imdbId = movie.getImdbId();
        APIClient.getById(imdbId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Failed to fetch details for " + movie.getMovieName(), Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    Gson gson = new Gson();
                    Movie detailedMovie = gson.fromJson(jsonData, Movie.class);

                    requireActivity().runOnUiThread(() -> {
                        movieList.add(detailedMovie);
                        movieAdapter.notifyDataSetChanged();
                    });
                } else {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(requireContext(), "Failed to fetch details for " + movie.getMovieName(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }

    @Override
    public void onClick(View v, int pos) {
        Movie movie = movieList.get(pos);

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Map to store user data
        Map<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("id", userId);

        // Retrieve the current favourites list and add the new movie
        db.collection("Users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    // Fetch list first
                    ArrayList<Map<String, Object>> favourites = (ArrayList<Map<String, Object>>) documentSnapshot.get("favourites");

                    if (favourites == null) {
                        favourites = new ArrayList<>();
                    }

                    // Check if the movie is already in favourites
                    boolean movieExists = false;
                    for (Map<String, Object> fav : favourites) {
                        if (Objects.equals(fav.get("imdbId"), movie.getImdbId())) {
                            movieExists = true;
                            break;
                        }
                    }

                    if (movieExists) {
                        // TODO: Remove the movie from favourites and change the icon
                        Toast.makeText(requireContext(), movie.getMovieName() + " removed from favourites!", Toast.LENGTH_SHORT).show();
                    } else {
                        // convert movie to map and add it to the list
                        Map<String, Object> movieMap = Movie.movieToMap(movie);
                        favourites.add(movieMap);

                        // update the list in the database
                        userDataMap.put("favourites", favourites);

                        // save updated favourites list
                        db.collection("Users").document(userId)
                                .set(userDataMap)
                                .addOnSuccessListener(aVoid -> Toast.makeText(requireContext(), movie.getMovieName() + " added to favourites!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed to add favourite", Toast.LENGTH_SHORT).show());
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Error fetching user data", Toast.LENGTH_SHORT).show());
    }
}