package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;

import java.util.List;
import java.util.Optional;

public interface IMovieDAO {

    /**
     * Retrieves all Movies from the database and store into a list
     *
     * @return list of Movies
     */
    List<Movie> getAllMovies();

    /**
     * Retrieves all Movies from the database by categoryId and store into a list
     *
     * @param categoryId that could have all movies
     * @return Optional list of Movies - not all movies can be found in that category
     */
    Optional<List<Movie>> getAllMoviesInTheCategoryById(int categoryId);

    /**
     * Retrieves optional Movie by its id
     *
     * @param id of movie that will be retrieved
     * @return Optional Movie by its id that might not be there
     */
    Optional<Movie> getMovieById(int id);

    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie that will be created
     * @return @Identity -> id of the movie
     */
    int createMovieTest(Movie movie);

    /**
     * Add Category to movie and is inserted into joining table
     *
     * @param category that its id will be inserted into the table
     * @param movie    that its id will be inserted into the table
     * @return affected rows 0 or 1 - 0 fail | 1 - success
     */

    int addCategoryToMovie(Category category, Movie movie);

    /**
     * Updates a movie in the database depending on the id of the movie
     *
     * @param movie that will be updated
//     * @param id    by its will be deleted -> this could be done better but works for now
     * @return @Identity id of updated movie
     */
    int updateMovieById(Movie movie);

    /**
     * Update timeStamp when a movie is played based on the id of the movie and date of opening
     *
     * @param id id of movie to be played
     * @return number of rows affected
     */
    int updateTimeStamp(String date,int id);

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
