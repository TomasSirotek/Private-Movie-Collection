package com.movie_collection.bll.services.api;

import com.movie_collection.bll.services.api.contract.IMovie;
import com.movie_collection.bll.services.interfaces.IAPIService;
import com.movie_collection.gui.DTO.MovieDTO;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;

public class APIService implements IAPIService {
    private static final String API_URL = "http://www.omdbapi.com/";

    /**
     * method to create new instance and configure on the way
     * @return IMovie interface that calls the api
     */
    private IMovie privateBuiltDto(){
        return Feign.builder() // creates new instance to config client
                .requestInterceptor(new ApiKeyRequestInterceptor())
                .decoder(new GsonDecoder()) // parse Json data from API - library added for this
                .logger(new Logger.JavaLogger("Movie.Logger").appendToFile("logs/http.log"))
                .logLevel(Logger.Level.FULL)
                .target(IMovie.class,API_URL); // spec target for the client
    }

    @Override
    public MovieDTO getMovieByTitle(String title) {
        IMovie movieCall = privateBuiltDto();
        System.out.println(movieCall.movieByName(title));
        return movieCall.movieByName(title);
    }


}
