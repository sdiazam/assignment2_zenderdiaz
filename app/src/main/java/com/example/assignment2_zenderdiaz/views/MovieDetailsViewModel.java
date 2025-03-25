package com.example.assignment2_zenderdiaz.views;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2_zenderdiaz.model.Movie;
import com.example.assignment2_zenderdiaz.utils.APIClient;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailsViewModel extends ViewModel {
    private final MutableLiveData<Movie> movie = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Movie> getMovie() {
        return movie;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void fetchMovieById(String imdbId) {
        String fullUrl = "https://www.omdbapi.com/?i=" + imdbId + "&apikey=731b4abe";
        Request request = new Request.Builder().url(fullUrl).build();
        OkHttpClient client = new OkHttpClient();

        APIClient.getById(imdbId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                errorMessage.postValue("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Movie movieDetails = gson.fromJson(json, Movie.class);
                    movie.postValue(movieDetails);
                } else {
                    errorMessage.postValue("API Error: " + response.code());
                }
            }
        });

    }
}

