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
import java.util.List;

public class MovieModel implements  IMovieModel{

    private final IMovieService movieService;

    private ObservableList<Movie> movies;

    @Inject
    public MovieModel(IMovieService movieService) {
        this.movieService = movieService;
        try {
            getAllMovies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Movie> getAllMovies() throws SQLException {
        List<Movie> temp = movieService.getAllMovies();
        movies = FXCollections.observableArrayList(temp);
        return movies;
    }

    @Override
    public ObservableList<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException{
        return FXCollections.observableArrayList(
                movieService.getAllMoviesInTheCategory(categoryId)
        );
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
    public void searchMovies(String query) throws SQLException {
        List<Movie> temp = movieService.getAllMovies();
        List<Movie> searched = movieService.searchMovie(temp, query);

        movies = FXCollections.observableArrayList(searched);
        searched.forEach(System.out::println);
        System.out.println("bitch here -> " + movies );
        if(movies.size() > 0 ){
            movies.clear();
            movies.addAll(searched);
        }

    }
}
