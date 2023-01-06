package com.movie_collection.gui.controllers;

import com.movie_collection.gui.controllers.abstractController.RootController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateMovieController extends RootController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hello from create movie controller window");
    }
}
