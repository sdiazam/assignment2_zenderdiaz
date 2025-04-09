package com.example.assignment3_diaz.model;

import java.util.ArrayList;

public class User {
    public User() {}

    public ArrayList<Movie> favourites;

    public ArrayList<Movie> getFavorites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Movie> favourites) {
        this.favourites = favourites;
    }
}
