package com.movie_collection.bll.util;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;

import java.util.ArrayList;
import java.util.List;

public class Filter implements IFilter {
    public List<Movie> filteringMovies(List<Movie> listToSearch,String query) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie m : listToSearch) {
            if (m.name().getValue().toLowerCase().contains(query.toLowerCase()) || checkCategories(m, query)) {
                filtered.add(m);
            }
        }
        return filtered;

    }
    private boolean checkCategories(Movie m, String query){
        List<Category> categories = m.categories();
        String categoryNames = "";
        for (Category c: categories) {
            categoryNames += c.name().getValue().toLowerCase();
        }
        return categoryNames.contains(query.toLowerCase());
    }

//        ObservableList<Movie> Movies =  MovieModel.getAllMovies();
//        FilteredList<Movie> filteredMovies = new FilteredList<>(Movies, b -> true);
//        filteredMovies.setPredicate(Movie ->{
//            if (Movie.name().get().contains(query.toLowerCase())
//                    || Movie.categories().contains(query.toLowerCase()))
//                return true;
//            else
//                return false;
//        });
//        return filteredMovies;
    }

