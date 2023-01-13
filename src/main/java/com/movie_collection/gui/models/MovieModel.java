package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.DTO.MovieDTO;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MovieModel implements  IMovieModel{

    private final IMovieService movieService;

    private final ObservableList<Movie> allMovies;
    private final ObservableList<Movie> filteredMovies;

    @Inject
    public MovieModel(IMovieService movieService) throws SQLException {
        this.movieService = movieService;
        this.filteredMovies = FXCollections.observableArrayList();
        this.allMovies = FXCollections.observableArrayList();
        getAllMovies();
        filteredMovies.setAll(allMovies);

    }

    @Override
    public ObservableList<Movie2> getAllMovies() {
        allMovies.setAll(FXCollections.observableArrayList(movieService.getAllMovies()));
        filteredMovies.setAll(allMovies);
        return allMovies; // so we only return here
    }

    @Override
    public ObservableList<Optional<List<Movie2>>> getAllMoviesInTheCategory(int categoryId) {
        allMovies.setAll(FXCollections.observableArrayList(movieService.getAllMoviesInTheCategory(categoryId)));
        filteredMovies.setAll(allMovies); // but we never retur nhere ?
        return null;
        // this needs to be looked at later
//        return FXCollections.observableArrayList(
//                movieService.getAllMoviesInTheCategory(categoryId)
//        );
    }

    @Override
    public ObservableList<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    @Override
    public int createMovie(Movie2 movie) {
        return movieService.createMovie(movie);
    }
    @Override
    public int updateMovie(Movie2 movie) {
        return movieService.updateMovie(movie);
    }
    @Override
    public int deleteMovie(int id) throws SQLException {
        return movieService.deleteMovie(id);
    }

    @Override
    public int updateTimeStamp(int id) throws SQLException {
        return movieService.updateTimeStamp(id);
    }

    @Override
    public MovieDTO findMovieByNameAPI(String title) {
        return movieService.getMovieByNameAPI(title);
    }

    @Override
    public void searchMovies(String query, CompareSigns buttonValue, double spinnerValue) {
        filteredMovies.setAll(movieService.searchMovie(allMovies, query, buttonValue, spinnerValue));
    }
}
