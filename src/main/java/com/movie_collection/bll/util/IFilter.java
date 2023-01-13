package com.movie_collection.bll.util;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;

import java.util.List;

public interface IFilter {
    List<Movie> filteringMovies(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue);
}
