package com.movie_collection.bll.services.interfaces;

import com.movie_collection.gui.DTO.MovieDTO;

public interface IAPIService {
    /**
     * method to retrieve movie by title
     *
     * @param title used as query
     * @return MovieDTO that can serve later for gui
     */
    MovieDTO getMovieByTitle(String title);
}
