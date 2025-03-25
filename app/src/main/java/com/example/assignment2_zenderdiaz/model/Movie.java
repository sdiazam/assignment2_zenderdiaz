package com.example.assignment2_zenderdiaz.model;

import com.google.gson.annotations.SerializedName;

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

    public String getActors() { return actors; }
    public String getPlot() { return plot; }
}
