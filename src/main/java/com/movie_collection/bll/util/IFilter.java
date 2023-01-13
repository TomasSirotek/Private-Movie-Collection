package com.movie_collection.bll.util;

import com.movie_collection.be.Movie2;
import com.movie_collection.bll.helpers.CompareSigns;

import java.util.List;

public interface IFilter {
    List<Movie2> filteringMovies(List<Movie2> listToSearch, String query, CompareSigns buttonValue, double spinnerValue);
}
