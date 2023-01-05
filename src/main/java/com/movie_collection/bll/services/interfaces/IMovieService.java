package com.movie_collection.bll.services.interfaces;

import java.util.List;

public interface IMovieService {
    /**
     * method tyo get list of all movies
     * @return List<Movie> to be created
     */
    List<String> getAllMovies();
}
