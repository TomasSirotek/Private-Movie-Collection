package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.services.interfaces.IMovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MovieModel implements IMovieModel{
    private final IMovieService movieService;

    @Inject
    public MovieModel(IMovieService categoryService) {
        this.movieService = categoryService;
    }

    @Override
    public ObservableList<Movie2> getAllMovies() {
        return FXCollections.observableArrayList(
                movieService.getAllMovies()
        );
    }

    @Override
    public ObservableList<Optional<List<Movie2>>> getAllMoviesInTheCategory(int categoryId) {
        return FXCollections.observableArrayList(
                movieService.getAllMoviesInTheCategory(categoryId)
        );
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
}
