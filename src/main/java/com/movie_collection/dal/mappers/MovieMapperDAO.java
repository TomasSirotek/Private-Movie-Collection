package com.movie_collection.dal.mappers;

import com.movie_collection.be.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovieMapperDAO {
    /**
     * Retrieves all Movies from the database and store into a list
     *
     * @return list of Movies
     */
    List<Movie> getAllMovies();

    /**
     * Retrieves optional Movie by its id
     *
     * @param id of movie that will be retrieved
     * @return Optional Movie by its id that might not be there
     */
    Movie getMovieById(@Param("movieId") int id);

    /**
     * Retrieves all Movies from the database by categoryId and store into a list
     *
     * @param categoryId that could have all movies
     * @return Optional list of Movies - not all movies can be found in that category
     */

    List<Movie> getAllMoviesByCategoryId(@Param("categoryId") int categoryId);

    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie
     * @return @Identity -> id of the movie
     */
    int createMovieTest(Movie movie);

    /**
     * Updates a movie in the database depending on the id of the movie
     *
//     * @param name   of the movie
//     * @param rating rating of the movie
//     * @param path   absolute path for the movie file
//     * @param id     that will be used to be recognized in the database
//     * @return @Identity id of updated movie
     */
    int updateMovie(Movie movie);

    /**
     * retrieves the result of an update movie last vied time
     * @param lastView of a snapshot date as String
     * @param id of movie that will get the updated date
     * @return affected rows
     */
    int updateTimeStamp(@Param("lastView") String lastView,@Param("movieId") int id);

    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int deleteMovieById(@Param("movieId") int id);

    /**
     * Add Category to movie and is inserted into joining table
     *
     * @param categoryId that will be added to joining table
     * @param movieId    that will be added to joining table
     * @return affected rows 0 or 1 - 0 fail | 1 - success
     */
    int addCategoryToMovie(@Param("categoryId") int categoryId, @Param("movieId") int movieId);

    /**
     * Removes a category from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int removeCategoryMovie(@Param("movieId") int id);


}
