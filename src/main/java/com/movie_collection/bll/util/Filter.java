package com.movie_collection.bll.util;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;

import java.util.ArrayList;
import java.util.List;

public class Filter implements IFilter {

    @Override
    public List<Movie> filteringMovies(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie m : listToSearch) {
            if ((m.getName().toLowerCase().contains(query.toLowerCase()) || checkCategories(m, query)) && checkRating(m, buttonValue, spinnerValue)) {
                filtered.add(m);
            }
        }
        return filtered;

    }

    private boolean checkCategories(Movie m, String query) {
        List<Category> categories = m.getCategories();
        StringBuilder categoryNames = new StringBuilder();
        for (Category c : categories) {
            categoryNames.append(c.getName().toLowerCase());
        }
        return categoryNames.toString().contains(query.toLowerCase());
    }

    private boolean checkRating(Movie m, CompareSigns buttonValue, double spinnerValue) {
        if (buttonValue == CompareSigns.LESS_THAN_OR_EQUAL) {
            return m.getRating() <= spinnerValue;
        } else if (buttonValue == CompareSigns.MORE_THAN_OR_EQUAL) {
            return m.getRating() >= spinnerValue;
        } else {
            return m.getRating() == spinnerValue;
        }
    }
}

