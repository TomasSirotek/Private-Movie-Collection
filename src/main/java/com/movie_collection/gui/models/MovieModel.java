package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.DTO.MovieDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class MovieModel implements  IMovieModel{

    private final IMovieService movieService;

    private final ObservableList<Movie2> allMovies;
    private final ObservableList<Movie2> filteredMovies;

    @Inject
    public MovieModel(IMovieService movieService){
        this.movieService = movieService;
        this.filteredMovies = FXCollections.observableArrayList();
        this.allMovies = FXCollections.observableArrayList();
        getAllMovies();
        filteredMovies.setAll(allMovies);

    }

    @Override
    public ObservableList<Movie2> getAllMovies() {
        return FXCollections.observableArrayList(
                movieService.getAllMovies()
        );
    }

    @Override
    public ObservableList<Movie2> getAllMoviesInTheCategory(int categoryId) {
        return movieService.getAllMoviesInTheCategory(categoryId)
                .map(FXCollections::observableArrayList).orElse(FXCollections.observableArrayList(Collections.emptyList()));
    }

    @Override
    public ObservableList<Movie2> getFilteredMovies() {
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
    public int deleteMovieById(int id) {
        return movieService.deleteMovie(id);
    }

    @Override
    public int updateTimeStamp(int id) {
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
