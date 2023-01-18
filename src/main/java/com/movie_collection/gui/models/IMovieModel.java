package com.movie_collection.gui.models;

import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.gui.DTO.MovieDTO;
import com.movie_collection.be.Movie;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface IMovieModel {
    
    ObservableList<Movie> getAllMovies();
    ObservableList<Movie> getAllMoviesInTheCategory(int categoryId);

    int createMovie(Movie movie) ;

    int updateMovie(Movie movie);

    int deleteMovieById(int id);

    List<Movie> searchMovies(String query, CompareSigns buttonValue, double spinnerValue);
	int updateTimeStamp(int id);

    MovieDTO findMovieByNameAPI(String title);

    boolean playVideoDesktop(int id, String path) throws IOException, InterruptedException;
}
