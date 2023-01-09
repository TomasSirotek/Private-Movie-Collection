package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for Movies with the view
 */
public class MovieController extends BaseController implements Initializable{

    @FXML
    private Label descrMovieTitle,descrIMDBRating;
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableColumn<Movie, Button> colPlayMovie,colEditMovies,colDeleteMovie;
    @FXML
    private TableColumn<Movie,String> colMovieTitle,movieYear,colMovieCategory;
    @FXML
    private TableColumn<Movie,String> colMovieRating;

    @Inject
    IMovieService movieService;

    @Inject
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTableWithData();
        listenToClickRow();
    }

    /**
     * method that check which row is selected and sets description
     */
    private void listenToClickRow() {
        moviesTable.setOnMouseClicked(event -> {
            Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                descrMovieTitle.setText(selectedMovie.name().getValue());
                descrIMDBRating.setText(String.valueOf(selectedMovie.rating()));
            }
        });
    }

    /**
     * method to fill table with initial data by the model
     */
    private void fillTableWithData(){
        // sets value factory for play column
        colPlayMovie.setCellValueFactory(col -> {
            Button playButton = new Button("▶️");
            playButton.setOnAction(e -> {
                //- > invoking to play movie in local player
            });
            return new SimpleObjectProperty<>(playButton);
        });
        colMovieTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name().getValue())); // set movie title
        movieYear.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name().getValue())); // does not have anything now from model
        colMovieRating.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().rating())));

        // sets value factory for movie category column data are collected by name and joined by "," -> action,horror
        colMovieCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().categories().stream()
                .map(Category::name)
                .map(StringProperty::getValue)
                .collect(Collectors.joining(","))
        ));
        // sets value factory for edit column
        colEditMovies.setCellValueFactory(col -> {
            Button editButton = new Button("⚙️");
            editButton.setOnAction(e -> {
                // _> edit functionality here
            });
            return new SimpleObjectProperty<>(editButton);
        });
        // sets value factory for delete  column
        colDeleteMovie.setCellValueFactory(col -> {
            Button deleteButton = new Button("❌");
            deleteButton.setOnAction(e -> {
                Movie movie = col.getValue(); // get movie object from the current row
                if (movie != null) {
                   int result =  tryDeleteMovie(movie.id()); // tries to delete movie by id inside the row
                    refreshTableAndNotify(result,movie.id());
                }
            });
            return new SimpleObjectProperty<>(deleteButton);
        });
        // tries to call movie service and set all items
        try {
            moviesTable.getItems().setAll(movieService.getAllMovies());
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO: Lets look at this later to fi it
        }
    }

    private void refreshTableAndNotify(int result,int id) {
        if(result > 0){
            refreshTable();
            System.out.println("Successfully deleted movie with id: "+ id); // place for out notification
        }else {
            System.out.println("Could not delete movie with id: " + id); // place for our notification
        }
    }

    /**
     * method that clears table items if they are not null and sets it back to required values
     */
    protected void refreshTable() {
        if(moviesTable != null){
            if(moviesTable.getItems() != null){
                moviesTable.getItems().clear();
                try {
                    moviesTable.getItems().setAll(movieService.getAllMovies());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * method that tries to delete movie by id
     * result success if > 0 ... else err display/handel
     * @param id of movie that will be deleted
     */
    private int tryDeleteMovie(int id) {
        try {
            return movieService.deleteMovie(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
