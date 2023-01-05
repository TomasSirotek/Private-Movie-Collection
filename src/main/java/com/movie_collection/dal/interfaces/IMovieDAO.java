package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieDAO {
    /**
     * Creates a new movie in the database with given properties
     * @param movie an object of type Movie with necessary properties
     * @throws SQLException if the connection to the database fails
     */
    void createMovie(Movie movie) throws SQLException;

    /**
     * Removes a movie from the database based on the id
     * @param id id of movie to be deleted
     * @throws SQLException if the connection to the database fails
     */
    void deleteMovie(int id) throws SQLException;

    /**
     * Updates a movie in the database depending on the id of the movie
     * @param movie a movie object with the new values
     * @throws SQLException if the connection to the database fails
     */
    void updateMovie(Movie movie) throws SQLException;

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
}
