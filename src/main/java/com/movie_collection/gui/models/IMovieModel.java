package com.movie_collection.gui.models;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IMovieModel {


    /*
     * abstract method to retrieve all the Movies
     * @return Observable list with all its Movies objects
     */
    ObservableList<Movie> getAllMovies() throws SQLException;
}
