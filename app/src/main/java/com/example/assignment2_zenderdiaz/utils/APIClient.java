package com.example.assignment2_zenderdiaz.utils;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class APIClient {
    static final OkHttpClient client = new OkHttpClient();
    static final String BASE_URL = "http://www.omdbapi.com/?s=";
    static final String API_KEY = "&apikey=731b4abe";

    public static void get(String title, Callback callback){
        final String url = BASE_URL + title + API_KEY;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}