package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

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
        filteredMovies.addAll(allMovies);

    }

    @Override
    public ObservableList<Movie> getAllMovies() throws SQLException {
        allMovies.setAll(FXCollections.observableArrayList(movieService.getAllMovies()));
        filteredMovies.setAll(allMovies);
        return allMovies;
    }

    @Override
    public ObservableList<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    @Override
    public void getAllMoviesInTheCategory(int categoryId) throws SQLException{
        allMovies.setAll(FXCollections.observableArrayList(movieService.getAllMoviesInTheCategory(categoryId)));
        filteredMovies.setAll(allMovies);
    }

    @Override
    public int createMovie(Movie movie) throws SQLException {
        return movieService.createMovie(movie);
    }
    @Override
    public int updateMovie(Movie movie) throws SQLException {
        return movieService.updateMovie(movie);
    }
    @Override
    public int deleteMovie(int id) throws SQLException {
        return movieService.deleteMovie(id);
    }
    @Override
    public void searchMovies(String query, String buttonText, double spinnerValue) {
        filteredMovies.setAll(movieService.searchMovie(allMovies, query, buttonText, spinnerValue));
    }
}
