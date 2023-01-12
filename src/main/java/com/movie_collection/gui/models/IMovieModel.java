package com.movie_collection.gui.models;

import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IMovieModel {
    
    ObservableList<Movie2> getAllMovies() throws SQLException;
    ObservableList<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException;

    int createMovie(Movie2 movie) ;

    int deleteMovie(int id) throws SQLException;

    int updateMovie(Movie movie) throws SQLException;

}
