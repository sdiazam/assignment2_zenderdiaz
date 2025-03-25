package com.example.assignment2_zenderdiaz.views;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2_zenderdiaz.model.Movie;
import com.example.assignment2_zenderdiaz.model.MovieResponse;
import com.example.assignment2_zenderdiaz.utils.APIClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainViewModel extends ViewModel {

    // live data
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchMovies(String title) {
        isLoading.postValue(true);

        APIClient.get(title, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                isLoading.postValue(false);
                errorMessage.postValue("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                isLoading.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    Gson gson = new Gson();
                    MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);

                    if (movieResponse != null && movieResponse.getSearch() != null) {
                        movies.postValue(movieResponse.getSearch());
                    } else {
                        errorMessage.postValue("No movies found.");
                    }
                } else {
                    errorMessage.postValue("API Error: " + response.code());
                }
            }
        });
    }
}
