package com.movie_collection.bll.services.api;


import com.movie_collection.gui.DTO.MovieDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("Accept: application/json")
public interface IMovie {
      @Headers({"Content-Type: application/json","apikey: {apikey}"})
      @RequestLine("GET /?t={title}")
      MovieDTO movieByName(@Param("title") String title);
}
