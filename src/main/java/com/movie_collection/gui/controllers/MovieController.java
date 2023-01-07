package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;
import javafx.beans.property.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for Movies with the view
 */
public class MovieController extends BaseController implements Initializable{

    @FXML
    private Label descrMovieTitle;
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableColumn<Movie, Button> colPlayMovie,colEditMovies,colDeleteMovie;
    @FXML
    private TableColumn<Movie,String> colMovieTitle,movieYear,colMovieCategory;
    @FXML
    private TableColumn<Movie,Void> colMovieRating;

    @Inject
    IMovieService movieService;

    @Inject
    public MovieController(ControllerFactory rootController, IMovieService movieService)  {
        super(rootController);
        this.movieService = movieService;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listenToClickRow();
        try {
            fillTableWithData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void listenToClickRow() {
        moviesTable.setOnMouseClicked(event -> {
            Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                descrMovieTitle.setText(selectedMovie.name().getValue());
            }
        });
    }

    private void fillTableWithData() throws SQLException {
        colPlayMovie.setCellValueFactory(col -> {
            Button playButton = new Button("▶️");
            playButton.setOnAction(e -> {
                //- > invoking to play movie in local player
            });
            return new SimpleObjectProperty<>(playButton);
        });
        colMovieTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name().getValue()));
        movieYear.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name().getValue())); // does not have anything now from model


        // FOR NOW THIS IS JUST GETTING THE INDEX OF EACH ONE
        colMovieRating.setCellFactory(col -> new TableCell<Movie, Void>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index));
                }
            }
        });

        colMovieCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().categories().stream()
                .map(Category::name)
                .map(StringProperty::getValue)
                .collect(Collectors.joining(","))
        ));
        colEditMovies.setCellValueFactory(col -> {
            Button editButton = new Button("⚙️");
            editButton.setOnAction(e -> {
                // _> edit functionality here
            });
            return new SimpleObjectProperty<>(editButton);
        });
        colDeleteMovie.setCellValueFactory(col -> {
            Button deleteButton = new Button("❌");
            deleteButton.setOnAction(e -> {
               // needs to get the id of the row
                // service to delete
                // call to refresh
            });
            return new SimpleObjectProperty<>(deleteButton);
        });
        moviesTable.getItems().setAll(movieService.getAllMovies());
    }


}
