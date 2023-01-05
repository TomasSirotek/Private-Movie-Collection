package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for Movies with the view
 */
public class MovieController extends BaseController implements Initializable{

    @Inject
    IMovieService movieService;

    @Inject
    public MovieController(ControllerFactory rootController, IMovieService movieService) {
        super(rootController);
        this.movieService = movieService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(movieService.getAllMovies());
    }
}
