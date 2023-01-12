package com.movie_collection.bll.util;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;

import java.util.ArrayList;
import java.util.List;

public class Filter implements IFilter {
    public List<Movie> filteringMovies(List<Movie> listToSearch, String query, String buttonText, double spinnerValue) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie m : listToSearch) {
            if ((m.name().getValue().toLowerCase().contains(query.toLowerCase()) || checkCategories(m, query)) && checkRating(m, buttonText, spinnerValue) ) {
                filtered.add(m);
            }
        }
        return filtered;

    }

    private boolean checkCategories(Movie m, String query) {
        List<Category> categories = m.categories();
        String categoryNames = "";
        for (Category c : categories) {
            categoryNames += c.name().getValue().toLowerCase();
        }
        return categoryNames.contains(query.toLowerCase());
    }

    private boolean checkRating(Movie m, String buttonText, double spinnerValue){
        if (buttonText.equals("<=")){
            return m.rating() <= spinnerValue;
        } else if (buttonText.equals(">=")) {
            return m.rating() >= spinnerValue;
        } else {
            return m.rating() == spinnerValue;
        }
    }
}

