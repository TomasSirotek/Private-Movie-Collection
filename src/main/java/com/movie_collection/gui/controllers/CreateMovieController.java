package com.movie_collection.gui.controllers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.EventType;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.controllers.event.RefreshEvent;
import com.movie_collection.gui.models.ICategoryModel;
import com.movie_collection.gui.models.IMovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateMovieController extends RootController implements Initializable {

    @FXML
    private Label labelMovieWindow;
    @FXML
    private Spinner<Double> personalRatingSpin;
    @FXML
    private TextField movieName,path;
    @FXML
    public Button onClickSelectFile,confirm_action,cancelOnAction;
    @FXML
    private MenuButton categoryMenuButton;
    private final IMovieModel movieModel;
    private final ICategoryModel categoryModel;

    private Movie editableMovie;
    private boolean isEditable = false;

    private final EventBus eventBus;

    @Inject
    public CreateMovieController(IMovieModel movieModel, ICategoryModel categoryModel, EventBus eventBus) {
        this.movieModel = movieModel;
        this.categoryModel = categoryModel;
        this.eventBus = eventBus;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCategorySelection();
        setComponentRules();
        onClickSelectFile.setOnAction(this::selectFileChooser);
        cancelOnAction.setOnAction(e -> getStage().close());
    }

    /**
     * method that takes care of setting the file chooser to be active
     * @param actionEvent triggered event
     */
    private void selectFileChooser(ActionEvent actionEvent) {
        var chooseFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.mp4,*.mpeg4)", "*.mp4", "*.mpeg4");
        chooseFile.getExtensionFilters().add(extFilter);

        File selectedMovieFile = chooseFile.showOpenDialog(new Stage());
        if (selectedMovieFile != null) {
            path.setText(selectedMovieFile.toURI().toString().substring(6));
            if (movieName.getText().isBlank())
                movieName.setText(selectedMovieFile.getName());
        }
    }

    /**
     * takes care of the value factory for spinner once instantiated
     */
    private void setComponentRules() {
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 10.0,1.0, 0.5);
        personalRatingSpin.setValueFactory(valueFactory);
    }

    /**
     * fill all the categories from all available categories
     */
    private void fillCategorySelection() {
        List<Category> categoryList = tryToGetCategory();
        if(categoryMenuButton.getItems() != null){
            categoryMenuButton.getItems().clear();
            categoryList.stream()
                    .map(category -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(category.getName());

                        return menuItem;
                    })
                    .forEach(menuItem -> categoryMenuButton.getItems().add(menuItem));
        }
    }

    /**
     * method that will construct new Movie from user input and tries to create it
     * this is two way purpose and the whole logic depends on the boolean value of editable
     * @param e action event
     */
    @FXML
    private void movieOnClickAction(ActionEvent e) {
        if(!isEditable){
            if(isValidatedInput()){
                var collectedCategory = mapSelectedCategories();

                Movie movie = new Movie();
                movie.setName(movieName.getText().trim());
                movie.setRating(personalRatingSpin.getValue());
                movie.setPath(path.getText().trim());
                movie.setCategories(collectedCategory);

                int result = tryCreateMovie(movie);
                closeAndUpdate(result, movie.getId());
                e.consume();

                movieModel.getAllMovies();

            }
        }else {
            if(isValidatedInput()){
                var collectedCategory = mapSelectedCategories();

                Movie movie = new Movie();
                movie.setId(editableMovie.getId());
                movie.setName(movieName.getText().trim());
                movie.setRating(personalRatingSpin.getValue());
                movie.setPath(path.getText().trim());
                movie.setCategories(collectedCategory);

                int result = tryUpdateMovie(movie);
                closeAndUpdate(result, movie.getId());

                e.consume();
            }

        }
    }

    /**
     * method that sets movie to be editable from passed movie object
     * @param movie object that will be displayed to be edited
     */
    protected void setEditableView(Movie movie){
        isEditable = true;
        editableMovie = movie;

        movieName.setText(editableMovie.getName());

        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 10.0, editableMovie.getRating(),0.5);
        personalRatingSpin.setValueFactory(valueFactory);
        path.setText(editableMovie.getPath());

        if(categoryMenuButton.getItems() != null){
            categoryMenuButton.getItems().clear();
            setEditableCategories();

        }
        confirm_action.setText("Update");
        labelMovieWindow.setText("Update Movie");
    }

    /**
     * load and sets correctly all the categories depends if movie has them
     */
    private void setEditableCategories() {
        List<Category> categoryList = tryToGetCategory();
        Set<String> categorySet = editableMovie.getCategories().stream().map(Category::getName).collect(Collectors.toSet());

        categoryList.stream()
                .map(category -> {
                    CheckMenuItem menuItem = new CheckMenuItem();
                    menuItem.setText(category.getName());

                    if(categorySet.contains(category.getName())){
                        menuItem.setSelected(true);
                    }

                    return menuItem;
                })
                .forEach(menuItem -> categoryMenuButton.getItems().add(menuItem));
    }

    /**
     * validates user input
     * @return false is requirements not met
     */
    private boolean isValidatedInput() {
        boolean isValidated = false;
        if(movieName.getText().isEmpty() || mapSelectedCategories().isEmpty() || path.getText().isEmpty()){
            AlertHelper.showDefaultAlert("Please fill all the field! You get the drill" , Alert.AlertType.ERROR);
        }else  {
            isValidated = true;
        }
        return isValidated;
    }

    /**
     * maps selected categories
     * @return list of Categories
     */
    private List<Category> mapSelectedCategories() {

        List<Category> categories = new ArrayList<>();
        categoryMenuButton.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem)
                .map(CheckMenuItem.class::cast)
                .filter(CheckMenuItem::isSelected)
                .forEach(button -> {
                    Category category = new Category();
                    category.setName(button.getText());
                    categories.add(category);
                });
        return categories;
    }

    /**
     * method to check if result was > 0 and decide if refresh table and close or notifies user that something went wrong
     * @param result from model with stored int
     * @param id of the movie that is currently being deleted
     */
    private void closeAndUpdate(int result,int id) {
        if(result > 0){
            eventBus.post(new RefreshEvent(EventType.UPDATE_TABLE_VIEW));
            getStage().close();
            AlertHelper.showDefaultAlert("Success with id: "+ id, Alert.AlertType.INFORMATION);
        }else {
            AlertHelper.showDefaultAlert("Successfully error occurred with id: "+ id, Alert.AlertType.ERROR);
        }

    }

    /**
     * method that tries to pass movie object and create it in database
     * @param movie object that will be created
     * @return 0 or 1 where 0 is fail and 1 is success
     */
    private int tryCreateMovie(Movie movie) {
        return movieModel.createMovie(movie);
    }

    /**
     * method that tries to pass movie object and update it in database
     * @param movie object that will be updated
     * @return 0 or 1 where 0 is fail and 1 is success
     */

    private int tryUpdateMovie(Movie movie) {
            return movieModel.updateMovie(movie);
    }

    /**
     * tries to get the List of categories
     * @return list of Categories with id,name
     */
    private List<Category> tryToGetCategory() {
        return categoryModel.getAllCategories();

    }

    public void close_stage(ActionEvent actionEvent) {
        getStage().close();
        actionEvent.consume();
    }
}
