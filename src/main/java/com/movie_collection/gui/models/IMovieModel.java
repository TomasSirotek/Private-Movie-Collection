package com.movie_collection.gui.models;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.gui.DTO.MovieDTO;
import com.movie_collection.be.Movie2;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMovieModel {
    
    ObservableList<Movie2> getAllMovies();
    ObservableList<Optional<List<Movie2>>> getAllMoviesInTheCategory(int categoryId);

    ObservableList<Movie> getFilteredMovies();

    int createMovie(Movie2 movie) ;

    int updateMovie(Movie2 movie);

    int deleteMovieById(int id);

    void searchMovies(String query, CompareSigns buttonValue, double spinnerValue);
	int updateTimeStamp(int id);

    MovieDTO findMovieByNameAPI(String title);
}
