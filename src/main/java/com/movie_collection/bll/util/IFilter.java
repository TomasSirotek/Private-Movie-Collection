package com.movie_collection.bll.util;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;

import java.util.List;

public interface IFilter {

    /**
     * method to filter specific movie titles or part of title, one or multiple genres and/or specific minimum imdb rating.
     * @param listToSearch that will be iterated through
     * @param query that will be searched
     * @param buttonValue of spinner enums to indicate symbols
     * @param spinnerValue rating value that will be searched
     * @return list of desired filtered movies
     */
    List<Movie> filteringMovies(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue);
}
