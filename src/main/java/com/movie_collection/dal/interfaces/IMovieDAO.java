package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieDAO {
    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie an object of type Movie with necessary properties
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int createMovie(Movie movie) throws SQLException;

    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int deleteMovie(int id) throws SQLException;

    /**
     * Updates a movie in the database depending on the id of the movie
     *
     * @param movie a movie object with the new values
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int updateMovie(Movie movie) throws SQLException;

    /**
     * @param id integer of the movie id
     * @return returns a movie object with the id
     * @throws SQLException if the connection to the database fails
     */
    Movie getMovieById(int id) throws SQLException;

    /**
     * Class responsible for getting all movies from the database
     *
     * @return List of all movies
     * @throws SQLException if the connection to the database fails
     */
    List<Movie> getAllMovies() throws SQLException;

    /**
     * Class responsible for getting all movies in a given category
     *
     * @param categoryId id of the category
     * @return List of movies that are in the category
     * @throws SQLException if the connection to the database fails
     */
    List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException;
}
