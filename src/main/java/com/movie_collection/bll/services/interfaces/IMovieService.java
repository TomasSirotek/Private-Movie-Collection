package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.gui.DTO.MovieDTO;

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
     * @param movie
     * @return @Identity -> id of the movie
     */
    int createMovie(Movie movie);

    /**
     * Retrieves optional Movie by its id
     *
     * @param id of movie that will be retrieved
     * @return Optional Movie by its id that might not be there
     */
    Optional<Movie> getMovieById(int id);

    int updateMovie(Movie movie);


    /**
     * Removes a movie from the database based on the id
     *
     * @param id id of movie to be deleted
     * @return number of rows affected
     */
    int deleteMovie(int id);

    /**
     * Update timeStamp when a movie is played based on the id
     *
     * @param id id of movie to be played
     * @return number of rows affected
     * @throws SQLException if the connection to the database fails
     */
    int updateTimeStamp(int id);

    /**
     * docs
     *
     * @param listToSearch
     * @param query
     * @param buttonValue
     * @param spinnerValue
     * @return
     */

    List<Movie> searchMovie(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue);

    /**
     * method to retrieve movie by name with the api
     * @param title to be searched as query
     * @return
     */
    MovieDTO getMovieByNameAPI(String title);

    List<Movie> getWatchMovies();
}
