package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie2;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMovieDAO {

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
    Optional<List<Movie2>> getAllMoviesInTheCategoryById(int categoryId);

    /**
     * Retrieves optional Movie by its id
     *
     * @param id of movie that will be retrieved
     * @return Optional Movie by its id that might not be there
     */
    Optional<Movie2> getMovieById(int id);

    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie
     * @return @Identity -> id of the movie
     */
    int createMovieTest(Movie2 movie);

    /**
     * Add Category to movie and is inserted into joining table
     *
     * @param category2 that its id will be inserted into the table
     * @param movie2    that its id will be inserted into the table
     * @return affected rows 0 or 1 - 0 fail | 1 - success
     */

    int addCategoryToMovie(Category2 category2, Movie2 movie2);

    /**
     * Updates a movie in the database depending on the id of the movie
     *
     * @param movie a movie object with the new values
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */

    /**
     * Updates a movie in the database depending on the id of the movie
     *
     * @param movie that will be updated
     * @param id    by its will be deleted -> this could be done better but works for now
     * @return @Identity id of updated movie
     */
    int updateMovieById(Movie2 movie, int id);

    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int deleteMovieById(int id);

    /**
     * Removes a category from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int removeCategoryFromMovie(int id);
}
