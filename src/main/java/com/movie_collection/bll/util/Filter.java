package com.movie_collection.bll.util;

import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.helpers.CompareSigns;

import java.util.ArrayList;
import java.util.List;

public class Filter implements IFilter {
    public List<Movie2> filteringMovies(List<Movie2> listToSearch, String query, CompareSigns buttonValue, double spinnerValue) {
        List<Movie2> filtered = new ArrayList<>();
        for (Movie2 m : listToSearch) {
            if ((m.getName().toLowerCase().contains(query.toLowerCase()) || checkCategories(m, query)) && checkRating(m, buttonValue, spinnerValue) ) {
                filtered.add(m);
            }
        }
        return filtered;

    }

    private boolean checkCategories(Movie2 m, String query) {
        List<Category2> categories = m.getCategories();
        String categoryNames = "";
        for (Category2 c : categories) {
            categoryNames += c.getName().toLowerCase();
        }
        return categoryNames.contains(query.toLowerCase());
    }

    private boolean checkRating(Movie2 m, CompareSigns buttonValue, double spinnerValue){
        if (buttonValue == CompareSigns.LESS_THAN_OR_EQUAL){
            return m.getRating() <= spinnerValue;
        } else if (buttonValue == CompareSigns.MORE_THAN_OR_EQUAL) {
            return  m.getRating() >= spinnerValue;
        } else {
            return  m.getRating() == spinnerValue;
        }
    }
}

