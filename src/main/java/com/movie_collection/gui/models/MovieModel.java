package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MovieModel implements  IMovieModel{

    private final IMovieService movieService;

    private ObservableList<Movie> movies;



    @Inject
    public MovieModel(IMovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public ObservableList<Movie> getAllMovies() throws SQLException {
        return movies = FXCollections.observableArrayList(
                movieService.getAllMovies()
        );
    }
}
