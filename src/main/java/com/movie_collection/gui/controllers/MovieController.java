package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.models.IMovieModel;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.Scanner;
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

    private final IMovieModel movieModel;

    private static String TXT_CONTENT = "";

    @Inject
    public MovieController(IMovieModel movieModel) {
        this.movieModel = movieModel;

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
                //TODO MAKE A METHOD WITH ALL PLAY ACTION
                actionPlay(col);
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
                    int result = 0; // tries to delete movie by id inside the row
                    try {
                        result = tryDeleteMovie(movie.id());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    refreshTableAndNotify(result,movie.id());
                }
            });
            return new SimpleObjectProperty<>(deleteButton);
        });
        // tries to call movie service and set all items

        try {
            moviesTable.setItems(movieModel.getAllMovies());
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO: Lets look at this later to fi it
        }
    }

    private void actionPlay(TableColumn.CellDataFeatures<Movie, Button> col) {
        try {
            playVideoDesktop(col.getValue().id(), col.getValue().absolutePath().getValue());
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
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


    private void playVideoDesktop(int id, String path) throws IOException, InterruptedException {
        Runtime runTime = Runtime.getRuntime();
        if(!TXT_CONTENT.isEmpty()){
            String s[] = new String[]{TXT_CONTENT, path};
            try {
                movieModel.updateTimeStamp(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            runTime.exec(s);
        }
        else{
            System.out.println("FK U");
        }

    }


    /**
     * method that clears table items if they are not null and sets it back to required values
     */
    void refreshTable() {
        if(moviesTable.getItems() != null){
            moviesTable.getItems().clear();
            try {
                moviesTable.getItems().setAll(movieModel.getAllMovies());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * method that tries to delete movie by id
     * result success if > 0 ... else err display/handel
     * @param id of movie that will be deleted
     */
    private int tryDeleteMovie(int id) throws SQLException {
        return movieModel.deleteMovie(id);
    }


    protected void setPath(Path fileName, String mediaPlayerPath) {
        try {
            Files.writeString(fileName,mediaPlayerPath);
            TXT_CONTENT = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
