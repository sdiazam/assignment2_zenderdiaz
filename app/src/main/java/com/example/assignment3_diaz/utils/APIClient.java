package com.example.assignment3_diaz.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class APIClient {
    static final OkHttpClient client = new OkHttpClient();
    static final String BASE_URL = "https://www.omdbapi.com/?s=";
    static final String API_KEY = "&apikey=731b4abe";

    public static void get(String title, Callback callback){
        final String url = BASE_URL + title + API_KEY;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
    public static void getById(String imdbId, Callback callback) {
        String url = "https://www.omdbapi.com/?i=" + imdbId + "&apikey=731b4abe";
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
