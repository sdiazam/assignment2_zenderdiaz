package com.example.assignment3_diaz.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3_diaz.R;
import com.example.assignment3_diaz.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    FirebaseAuth mAuth;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        loadFragment(new MovieListFragment());

        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == 2131230961) { // home
                loadFragment(new MovieListFragment());
            } else {
                loadFragment(new FavouritesFragment()); // favourites
            }
            return true;
        });

        binding.searchButton.setOnClickListener(v -> {
            String title = Objects.requireNonNull(binding.searchBar.getText()).toString().trim();
            if (!title.isEmpty()) {
                viewModel.fetchMovies(title);
            } else {
                Toast.makeText(MainActivity.this, "Enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}
