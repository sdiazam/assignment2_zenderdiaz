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

import java.util.ArrayList;

public class MovieListFragment extends Fragment implements MovieClickListener {

    private FragmentMovieListBinding binding;
    private MovieAdapter movieAdapter;
    private final ArrayList<Movie> movieList = new ArrayList<>();
    private MainViewModel viewModel;

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
            movieList.addAll(movies);
            movieAdapter.notifyDataSetChanged();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v, int pos) {
        Toast.makeText(requireContext(), "You chose: " + movieList.get(pos).getMovieName(), Toast.LENGTH_SHORT).show();
    }
}
