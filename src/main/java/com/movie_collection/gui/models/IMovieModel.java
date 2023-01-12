package com.movie_collection.gui.models;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IMovieModel {
    
    ObservableList<Movie> getAllMovies() throws SQLException;
    void getAllMoviesInTheCategory(int categoryId) throws SQLException;
    ObservableList<Movie> getFilteredMovies();

    int createMovie(Movie movie) throws SQLException;

    int deleteMovie(int id) throws SQLException;
    int updateMovie(Movie movie) throws SQLException;

    void searchMovies(String query, CompareSigns buttonValue, double spinnerValue);
	int updateTimeStamp(int id) throws SQLException;
}
