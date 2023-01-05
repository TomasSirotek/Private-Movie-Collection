package com.movie_collection;

import com.movie_collection.be.Movie;
import com.movie_collection.dal.MovieDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

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
        // createMovieTest();
        //deleteMovieTest();
        updateMovieTest();




    }

    private static void createMovieTest() {
        try {
            Calendar cal = Calendar.getInstance();
            //cal.set(2021, Calendar.MARCH, 1);
            md.createMovie(new com.movie_collection.be.Movie(1, "test1", 10.0, "test", cal));
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
            Calendar cal = Calendar.getInstance();
            cal.set(2000, Calendar.JULY, 4);
            md.updateMovie(new Movie(1040, "updateTest", 7.3, "updateTestPath", cal));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}