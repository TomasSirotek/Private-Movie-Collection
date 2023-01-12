package com.movie_collection.gui.models;

import com.movie_collection.be.Movie;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IMovieModel {
    
    ObservableList<Movie> getAllMovies() throws SQLException;
    ObservableList<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException;

    int createMovie(Movie movie) throws SQLException;

    int deleteMovie(int id) throws SQLException;
    int updateMovie(Movie movie) throws SQLException;

	int updateTimeStamp(int id) throws SQLException;
}
