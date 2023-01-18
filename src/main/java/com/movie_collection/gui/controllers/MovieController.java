package com.movie_collection.gui.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.EventType;
import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.gui.DTO.MovieDTO;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import com.movie_collection.gui.controllers.event.RefreshEvent;
import com.movie_collection.gui.models.IMovieModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for Movies with the view
 */
public class MovieController extends RootController implements Initializable {

    // -> Optional table description
    @FXML
    private ImageView movieImage;
    @FXML
    private TextArea desPlot;
    @FXML
    private Label desReleased,
            desRunTime,
            desCast,
            desDirector,
            desImdbRating, desPrRating,
            descrMovieTitle, desMatRating;

    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableColumn<Movie, Button> colPlayMovie, colEditMovies, colDeleteMovie;
    @FXML
    private TableColumn<Movie, String> colMovieTitle, colMovieCategory;
    @FXML
    private TableColumn<Movie, Double> colMovieRating;

    private final IMovieModel movieModel;

    private final IControllerFactory controllerFactory;

    private boolean isCategoryView = false;

    private final EventBus eventBus;

    private int categoryId;

    @Inject
    public MovieController(Label descrIMDBRating, IMovieModel movieService, IControllerFactory controllerFactory, EventBus eventBus) {
        this.desImdbRating = descrIMDBRating;
        this.movieModel = movieService;
        this.controllerFactory = controllerFactory;
        this.eventBus = eventBus;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTableWithData();
        listenToClickRow();
        eventBus.register(this);
    }


    /**
     * method that check which row is selected and sets description
     */
    private void listenToClickRow() {
        moviesTable.setOnMouseClicked(event -> {
            Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                // tries to find the movie by name
                MovieDTO movieDTO = movieModel.findMovieByNameAPI(selectedMovie.getName());
                fillDescriptionWithAPIData(movieDTO,selectedMovie);
            }
        });
    }

    /**
     * fill all the description data
     * @param movieDTO
     * @param selectedMovie
     */
    private void fillDescriptionWithAPIData(MovieDTO movieDTO, Movie selectedMovie) {
        if(movieDTO.Poster != null){
            movieImage.setImage(new Image(movieDTO.Poster));
        }
        else{
            movieImage.setImage(new Image("file:src/main/resources/com/movie_collection/images/default_image.jpeg"));
        }

        descrMovieTitle.setText(selectedMovie.getName());
        desPlot.setText(movieDTO.Plot);
        desRunTime.setText(movieDTO.Runtime);
        desCast.setText(movieDTO.imdbRating);
        desReleased.setText(movieDTO.Released);
        desImdbRating.setText(movieDTO.imdbRating);
        desDirector.setText(movieDTO.Director);
        desPrRating.setText(String.valueOf(selectedMovie.getRating()));
        desMatRating.setText(movieDTO.Rated);
    }

    /**
     * method to fill table with initial data by the model
     */
    private void fillTableWithData() {
        // sets value factory for play column
        colPlayMovie.setCellValueFactory(col -> {
            Button playButton = new Button("▶");
            playButton.setOnAction(e -> {
                actionPlay(col);
            });
            return new SimpleObjectProperty<>(playButton);
        });
        // ->
        colMovieTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName())); // set movie title
        colMovieRating.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRating()));

        // sets value factory for movie category column data are collected by name and joined by "," -> action,horror
        colMovieCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.joining(","))
        ));
        // sets value factory for edit column
        colEditMovies.setCellValueFactory(col -> {
            Button editButton = new Button("⚙");
            Movie updateMovie = col.getValue();
            editButton.setOnAction(e -> {
                CreateMovieController controller = loadSetEditController(updateMovie);
                showUpdateWindow(controller.getView());
            });
            return new SimpleObjectProperty<>(editButton);
        });
        // sets value factory for delete  column
        colDeleteMovie.setCellValueFactory(col -> {
            Button deleteButton = new Button("❌");
            deleteButton.setOnAction(e -> {
                Movie movie = col.getValue(); // get movie object from the current row
                if (movie != null) {
                    var resultNotify = AlertHelper.showOptionalAlertWindow("Are you sure you want delete movie with id: " + movie.getId(),"", Alert.AlertType.CONFIRMATION);
                    if (resultNotify.isPresent() && resultNotify.get().equals(ButtonType.OK)) {
                        int result = tryDeleteMovie(movie.getId()); // tries to delete movie by id inside the row
                        refreshTableAndNotify(result,movie.getId());
                    }
                }
            });
            return new SimpleObjectProperty<>(deleteButton);
        });

        trySetTableWithMovies();
    }



    protected void setIsCategoryView(int categoryId) {
        this.isCategoryView = true;
        this.categoryId = categoryId;

        if (moviesTable != null) {
            moviesTable.getItems().clear();
            trySetTableByCategory(categoryId);
        }
    }


    private void showUpdateWindow(Parent view) {
        Stage stage = new Stage();
        Scene scene = new Scene(view);

        stage.initOwner(getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Update Movie");

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private CreateMovieController loadSetEditController(Movie updateMovie) {
        CreateMovieController controller;
        try {
            controller = (CreateMovieController) controllerFactory.loadFxmlFile(ViewType.CREATE_EDIT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.setEditableView(updateMovie);
        return controller;
    }


    /**
     * refreshed the table and notify user about the status of his actions
     *
     * @param result that will be judged up on
     * @param id     that will be displayed if action for that id was successful
     */

    private void refreshTableAndNotify(int result, int id) {
        if (result > 0) {
            refreshTable();
            AlertHelper.showDefaultAlert("Successfully deleted movie with id: " + id, Alert.AlertType.INFORMATION);
        } else {
            AlertHelper.showDefaultAlert("Could not delete movie with id: " + id, Alert.AlertType.ERROR);
        }
    }


    private void showMediaPlayerUnselected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Select your Media Player");
        alert.getButtonTypes().setAll(new ButtonType("OK"));
        alert.showAndWait();
    }


    /**
     * method that clears table items if they are not null and sets it back to required values
     */

    private void refreshTable() {
        if(moviesTable != null){
            if(moviesTable.getItems() != null){
                moviesTable.getItems().clear();
                moviesTable.getItems().setAll( movieModel.getAllMovies());
            }
        }
    }

    private void actionPlay(TableColumn.CellDataFeatures<Movie, Button> col) {
        try {
            if (!movieModel.playVideoDesktop(col.getValue().getId(), col.getValue().getPath())){
                showMediaPlayerUnselected();
            }
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void trySetTableByCategory(int categoryId){
        moviesTable.setItems(movieModel.getAllMoviesInTheCategory(categoryId));
    }

    /**
     * method that tries to set table with all movies
     */

    private void trySetTableWithMovies() {
        moviesTable.setItems(movieModel.getAllMovies());
    }

    /**
     * method that tries to delete movie by id
     * result success if > 0 ... else err display/handel
     * @param id of movie that will be deleted
     */
    private int tryDeleteMovie(int id) {
        return movieModel.deleteMovieById(id);
    }

    /**
     * Registering events
     */
    @Subscribe
    public void handleCategoryEvent(RefreshEvent event){
        if(event.getEventType() == EventType.UPDATE_TABLE_VIEW){
            refreshTable();
        }
    }
    }
