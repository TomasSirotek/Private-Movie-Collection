package com.movie_collection.bll.util;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.dal.dao.MovieDAO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Filter implements IFilter {
    public List<Movie> filteringMovies(List<Movie> listToSearch,String query) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie m :
                listToSearch) {
            if (m.name().getValue().toLowerCase().contains(query.toLowerCase()) ||
                    m.categories().contains(query.toLowerCase()
                   )) {
                filtered.add(m);
            }
        }
        return filtered;

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

