package com.movie_collection.bll.services;

import com.movie_collection.bll.services.interfaces.IMovieService;

import java.util.List;

public class MovieService implements IMovieService {
    @Override
    public List<String> getAllMovies() {
        return List.of("First","second");
    }
}
