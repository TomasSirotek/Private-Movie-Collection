package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.gui.DTO.MovieDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMovieService {
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
    Optional<List<Movie>> getAllMoviesInTheCategory(int categoryId);

    /**
     * Creates a new movie in the database with given properties
     *
     * @param movie to be created
     * @return @Identity -> id of the movie
     */
    int createMovie(Movie movie);

    /**
     * Updates a movie in the database depending on the id of the movie
     *
     * @param movie that will be updated
     * @return @Identity id of updated movie
     */

    int updateMovie(Movie movie);

    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int deleteMovie(int id);

    /**
     * method to filter specific movie titles or part of title, one or multiple genres and/or specific minimum imdb rating.
     *
     * @param listToSearch that will be iterated through
     * @param query        that will be searched
     * @param buttonValue  of spinner enums to indicate symbols
     * @param spinnerValue rating value that will be searched
     * @return list of desired filtered movies
     */

    List<Movie> searchMovie(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue);

    /**
     * method to retrieve movie by name with the api
     *
     * @param title to be searched as query
     * @return movie dto with fetched data
     */
    MovieDTO getMovieByNameAPI(String title);

    /**
     * method to get all watched movies
     *
     * @return list of movies there has lastView as not null
     */
    List<Movie> getWatchMovies();

    /**
     * method to play required files
     *
     * @param id   of the movie
     * @param path absolute to the file location
     */
    boolean playVideoDesktop(int id, String path) throws IOException;
}
