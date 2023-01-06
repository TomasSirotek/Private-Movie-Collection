package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieService {
    /**
     * method tyo get list of all movies
     *
     * @return List<Movie> to be created
     */
    List<Movie> getAllMovies() throws SQLException;

    /**
     * method to create a movie
     *
     * @param movie to be created
     * @return created movie
     */
    int createMovie(Movie movie) throws SQLException;
    int updateMovie(Movie movie) throws SQLException;
    int deleteMovie(int id) throws SQLException;
    Movie getMovieById(int id) throws SQLException;
    List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException;
}
