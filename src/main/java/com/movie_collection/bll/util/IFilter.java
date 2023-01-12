package com.movie_collection.bll.util;

import com.movie_collection.be.Movie;

import java.util.List;

public interface IFilter {
    List<Movie> filteringMovies(List<Movie> listToSearch, String query, String buttonText, double spinnerValue);
}
