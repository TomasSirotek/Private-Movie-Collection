package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMovieDAO {


    List<Movie2> getAllMoviesTest();

//    /**
//     * Class responsible for getting all movies in a given category
//     *
//     * @param categoryId id of the category
//     * @return List of movies that are in the category
//     * @throws SQLException if the connection to the database fails
//     */
    Optional<List<Movie2>> getAllMoviesInTheCategoryTest(int categoryId);

    /**
     * @param id integer of the movie id
     * @return returns a movie object with the id
     * @throws SQLException if the connection to the database fails
     */
    Optional<Movie2> getMovieById(int id);

    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie an object of type Movie with necessary properties
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int createMovieTest(Movie2 movie);

    int addCategoryToMovie(Category2 category2, Movie2 movie2);

    /**
     * Updates a movie in the database depending on the id of the movie
     *
     * @param movie a movie object with the new values
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int updateMovie(Movie2 movie,int id) ;

    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int deleteMovie(int id);

    int removeCategoryFromMovie(int id);
}
