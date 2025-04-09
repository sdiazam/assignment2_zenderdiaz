package com.example.assignment3_diaz.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Movie {
    @SerializedName("Title")
    private String movieName;

    @SerializedName("Year")
    private String year;

    @SerializedName("imdbID")
    private String imdbId;

    @SerializedName("Poster")
    private String posterUrl;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    private boolean isFavourited;

    public Movie() {}

    public String getMovieName() {
        return movieName;
    }

    public String getYear() {
        return year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public boolean getIsFavourited() {
        return isFavourited;
    }

    public static Map<String, Object> movieToMap(Movie movie) {
        Map<String, Object> map = new HashMap<>();
        map.put("movieName", movie.getMovieName());
        map.put("year", movie.getYear());
        map.put("imdbId", movie.getImdbId());
        map.put("posterUrl", movie.getPosterUrl());
        map.put("actors", movie.getActors());
        map.put("plot", movie.getPlot());
        return map;
    }
}
