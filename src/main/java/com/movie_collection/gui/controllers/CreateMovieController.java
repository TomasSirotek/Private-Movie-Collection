package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.models.IMovieModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateMovieController extends RootController implements Initializable {
    @FXML
    private Label labelUserAction;
    @FXML
    private Spinner<Double> personalRatingSpin;
    @FXML
    private TextField movieName,path;
    @FXML
    public Button onClickSelectFile,deleteOnAction,confirm_action,cancelOnAction;
    @FXML
    private ChoiceBox<Category> categoryChoiceBox;

    private boolean isEditable = false;
    private final IMovieModel movieModel;
    private final MovieController movieController;

    @Inject
    public CreateMovieController(IMovieModel movieModel, MovieController controller) {
        this.movieModel = movieModel;
        this.movieController = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirm_action.setOnAction(this::movieOnClickAction);

        cancelOnAction.setOnAction(e -> getStage().close()); // sets to close stage on action
    }

    /**
     * method that will construct new Movie from user input and tries to create it
     * this is two way purpose and the whole logic depends on the boolean value of editable
     * @param e action event
     */
    private void movieOnClickAction(ActionEvent e) {
        if(!isEditable){

            Movie movie = new Movie(
                    0,
                    new SimpleStringProperty(movieName.getText().trim()),
                    3.3,
                    new SimpleStringProperty(path.getText().trim()),
                    categoryChoiceBox.getItems(),
                    null);

            int result = tryCreateMovie(movie);
            closeAndUpdate(result,movie.id());
        }else {
            // update movie here
        }
    }


    /**
     * method to check if result was > 0 and decide if refresh table and close or notifies user that something went wrong
     * @param result from model with stored int
     * @param id of the movie that is currently being deleted
     */
    private void closeAndUpdate(int result,int id) {
        if(result > 0){
            movieController.refreshTable();
            getStage().close();
            System.out.println("Successfully created user with id: " + id );
        }else {
            System.out.println("Could not create movie with id: " + id);
        }

    }

    /**
     * method that tries to pass movie object and create it in database
     * @param movie object that will be created
     * @return 0 or 1 where 0 is fail and 1 is success
     */
    private int tryCreateMovie(Movie movie) {
        try {
            return movieModel.createMovie(movie);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
