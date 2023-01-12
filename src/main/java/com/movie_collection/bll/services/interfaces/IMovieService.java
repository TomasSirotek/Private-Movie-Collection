package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Movie2;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMovieService {
    /**
     * Retrieves all Movies from the database and store into a list
     *
     * @return list of Movies
     */
    List<Movie2> getAllMovies();

    /**
     * Retrieves all Movies from the database by categoryId and store into a list
     *
     * @param categoryId that could have all movies
     * @return Optional list of Movies - not all movies can be found in that category
     */
    Optional<List<Movie2>> getAllMoviesInTheCategory(int categoryId);

    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie
     * @return @Identity -> id of the movie
     */
    int createMovie(Movie2 movie);

    /**
     * Retrieves optional Movie by its id
     *
     * @param id of movie that will be retrieved
     * @return Optional Movie by its id that might not be there
     */
    Optional<Movie2> getMovieById(int id);

    int updateMovie(Movie2 movie);


    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int deleteMovie(int id) throws SQLException;

}
