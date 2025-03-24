package com.example.assignment2_zenderdiaz.model;

public class MovieModel {
    private String movieName;
    private String year;
    private String imdbId;
    private String type;
    private String posterUrl;
    private String actors;
    private String plot;

    public MovieModel(String movieName, String year, String imdbId, String type, String posterUrl, String actors, String plot) {
        this.movieName = movieName;
        this.year = year;
        this.imdbId = imdbId;
        this.type = type;
        this.posterUrl = posterUrl;
        this.actors = actors;
        this.plot = plot;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getYear() {
        return year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getType() {
        return type;
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
}
