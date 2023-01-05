package com.movie_collection;

import com.movie_collection.be.Movie;
import com.movie_collection.dal.MovieDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

public class HelloApplication extends Application {
    private static final MovieDAO md = new MovieDAO();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/base.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        //launch();

        //createMovieTest();
        //deleteMovieTest();
        //updateMovieTest();
        //getMovieTest();
        //getAllMoviesTest();
    }

    private static void createMovieTest() {
        try {
            Date date = new Date(Calendar.getInstance().getTime().getTime());
            md.createMovie(new com.movie_collection.be.Movie(1, "testnewdate", 10.0, "test", date));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private static void deleteMovieTest() {
        try {
            md.deleteMovie(1039);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void updateMovieTest() {
        try {
            Date date = new Date(Instant.now().toEpochMilli());
            md.updateMovie(new Movie(1041, "updateTest", 7.3, "updateTestPath", date));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getMovieTest() {
        try {
            Movie movie = md.getMovie(1017);
            System.out.println(movie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAllMoviesTest() {
        try {
            List<Movie> movies = md.getAllMovies();
            System.out.println(movies);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}