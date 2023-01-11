package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MovieModel implements  IMovieModel{

    private final IMovieService movieService;

    private ObservableList<Movie> allMovies;
    private ObservableList<Movie> filteredMovies;

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
        allMovies = FXCollections.observableArrayList(movieService.getAllMovies());
        return allMovies;
    }

    @Override
    public ObservableList<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException{
        return FXCollections.observableArrayList(
                movieService.getAllMoviesInTheCategory(categoryId)
        );
    }

    @Override
    public int createMovie(Movie movie) throws SQLException {
        getAllMovies();
        return movieService.createMovie(movie);
    }
    @Override
    public int updateMovie(Movie movie) throws SQLException {
        getAllMovies();
        return movieService.updateMovie(movie);
    }
    @Override
    public int deleteMovie(int id) throws SQLException {
        getAllMovies();
        return movieService.deleteMovie(id);
    }
    @Override
    public void searchMovies(String query) {
        if (query.length() > 0){
            filteredMovies = FXCollections.observableArrayList(movieService.searchMovie(allMovies, query));
        } else {
            filteredMovies = allMovies;
        }
        System.out.println("----");
        filteredMovies.forEach(movie -> System.out.println(movie.name().get()));
    }
}
