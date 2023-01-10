package com.movie_collection.bll.util;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.dal.dao.MovieDAO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.SQLException;
import java.util.List;

public class Filter {

    private MovieDAO movieDAO = new MovieDAO();

    //@Inject
   // private IMovieModel MovieModel;

/*

    public List<Movie> filteringMovies(String query) throws SQLException {


        ObservableList<Movie> Movies =  MovieModel.getAllMovies();
        FilteredList<Movie> filteredMovies = new FilteredList<>(Movies, b -> true);
        filteredMovies.setPredicate(Movie ->{
            if (Movie.name().get().contains(query.toLowerCase())
                    || Movie.categories().contains(query.toLowerCase()))
                return true;
            else
                return false;
        });
        return filteredMovies;
    }

 */
}
