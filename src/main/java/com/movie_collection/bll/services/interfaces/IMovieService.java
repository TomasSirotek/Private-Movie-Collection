package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMovieService {
    /**
     * method tyo get list of all movies
     *
     * @return List<Movie> to be created
     */
    List<Movie2> getAllMovies();

    /**
     * method to get all movies in a category
     *
     * @param categoryId of the category
     * @return List of movies in the category
     */


    Optional<List<Movie2>> getAllMoviesInTheCategory(int categoryId);
    /**
     * method to get a movie by id
     *
     * @param id of the movie
     * @return movie object
     */
    Optional<Movie2> getMovieById(int id);

    int updateMovie(Movie2 movie);
    /**
     * method to create a movie
     *
     * @param movie to be created
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int createMovie(Movie2 movie);

    /**
     * method to delete a movie
     *
     * @param id of the movie to be deleted
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int deleteMovie(int id) throws SQLException;

}
