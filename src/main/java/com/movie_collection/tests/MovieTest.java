package com.movie_collection.tests;

import com.movie_collection.be.Movie;
import com.movie_collection.dal.dao.MovieDAO;
import com.movie_collection.dal.daoInterface.IMovieDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

public class MovieTest {
    private final IMovieDAO md = new MovieDAO();
    public void createMovieTest() {
        try {
            Date date = new Date(Calendar.getInstance().getTime().getTime());
            md.createMovie(new com.movie_collection.be.Movie(1, "testnewdate", 10.0, "test", date));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteMovieTest() {
        try {
            md.deleteMovie(1037);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMovieTest() {
        try {
            Date date = new Date(Instant.now().toEpochMilli());
            md.updateMovie(new Movie(1037, "updateTest", 7.3, "updateTestPath", date));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getMovieTest() {
        try {
            Movie movie = md.getMovie(1017);
            System.out.println(movie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllMoviesTest() {
        try {
            List<Movie> movies = md.getAllMovies();
            System.out.println(movies);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
