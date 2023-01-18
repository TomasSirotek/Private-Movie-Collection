package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.DTO.MovieDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class MovieModel implements IMovieModel {

    private final IMovieService movieService;

    private ObservableList<Movie> allMovies;

    @Inject
    public MovieModel(IMovieService movieService) {
        this.movieService = movieService;
        this.allMovies = getAllMovies();
    }

    @Override
    public ObservableList<Movie> getAllMovies() {
        allMovies = FXCollections.observableArrayList(movieService.getAllMovies());
        return allMovies;
    }

    @Override
    public ObservableList<Movie> getAllMoviesInTheCategory(int categoryId) {
        return movieService.getAllMoviesInTheCategory(categoryId)
                .map(FXCollections::observableArrayList).orElse(FXCollections.observableArrayList(Collections.emptyList()));
    }

    @Override
    public int createMovie(Movie movie) {
        return movieService.createMovie(movie);
    }

    @Override
    public int updateMovie(Movie movie) {
        return movieService.updateMovie(movie);
    }

    @Override
    public int deleteMovieById(int id) {
        return movieService.deleteMovie(id);
    }

    @Override
    public MovieDTO findMovieByNameAPI(String title) {
        return movieService.getMovieByNameAPI(title);
    }

    @Override
    public List<Movie> getWatchedMovies() {
        return movieService.getWatchMovies();
    }

    @Override
    public List<Movie> searchMovies(String query, CompareSigns buttonValue, double spinnerValue) {
        return movieService.searchMovie(allMovies, query, buttonValue, spinnerValue);
    }

    @Override
    public boolean playVideoDesktop(int id, String path) throws IOException {
        return movieService.playVideoDesktop(id, path);
    }
}
