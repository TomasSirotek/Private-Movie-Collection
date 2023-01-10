package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieModel implements IMovieModel{
    private final IMovieService movieService;

   // private ObservableList<Movie> moviesList = FXCollections.observableArrayList();

    @Inject
    public MovieModel(IMovieService categoryService) {
        this.movieService = categoryService;
    }

    @Override
    public ObservableList<Movie> getAllMovies() throws SQLException {
        return FXCollections.observableArrayList(
                movieService.getAllMovies()
        );
    }

    @Override
    public int createMovie(Movie movie) throws SQLException {
        return movieService.createMovie(movie);
    }

    @Override
    public int deleteMovie(int id) throws SQLException {
        return movieService.deleteMovie(id);
    }

    @Override
    public int updateMovie(Movie movie) throws SQLException {
        return movieService.updateMovie(movie);
    }
}
