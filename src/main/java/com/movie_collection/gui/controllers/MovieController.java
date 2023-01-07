package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for Movies with the view
 */
public class MovieController extends BaseController implements Initializable{
    @FXML
    private TableColumn<Movie, Button> colPlayMovie;
    @FXML
    private TableColumn<Movie,String> colMovieTitle;
    @FXML
    private TableColumn<Movie, String> movieYear;
    @FXML
    private TableColumn<Movie,Double> colMovieRating;
    @FXML
    private TableColumn<Movie, String> colMovieCategory;
    @FXML
    private TableColumn<Movie,Button> colEditMovies;
    @FXML
    private TableColumn<Movie,Button> colDeleteMovie;
    @FXML
    private TableView<Movie> moviesTable;
    @Inject
    IMovieService movieService;

    @Inject
    public MovieController(ControllerFactory rootController, IMovieService movieService)  {
        super(rootController);
        this.movieService = movieService;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillTableWithData();
            System.out.println(movieService.getAllMovies().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTableWithData() throws SQLException {
        colPlayMovie.setCellValueFactory(col -> {
            Button playButton = new Button("▶️");
            playButton.setOnAction(e -> {
                //- > invoking to play movie in local browser
            });
            return new SimpleObjectProperty<>(playButton);
        });
        colMovieTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name().getValue()));
        movieYear.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name().getValue())); // does not have anything now from model

        colMovieRating.setCellFactory(col -> new TableCell<Movie, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(item));
                }}

        });
        colMovieCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().categories().stream()
                .map(Category::name)
                .map(StringProperty::getValue)
                .collect(Collectors.joining(","))
        ));
        colEditMovies.setCellValueFactory(col -> {
            Button playButton = new Button("⚙️");
            return new SimpleObjectProperty<>(playButton);
        });
        colDeleteMovie.setCellValueFactory(col -> {
            Button playButton = new Button("❌");
            return new SimpleObjectProperty<>(playButton);
        });

        moviesTable.getItems().setAll(movieService.getAllMovies());
    }


}
