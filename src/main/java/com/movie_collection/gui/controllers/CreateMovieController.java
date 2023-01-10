package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.models.ICategoryModel;
import com.movie_collection.gui.models.IMovieModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CreateMovieController extends RootController implements Initializable {

    @FXML
    private Label labelMovieWindow;
    @FXML
    private Spinner<Double> personalRatingSpin;
    @FXML
    private TextField movieName,path,durationField;
    @FXML
    public Button onClickSelectFile,confirm_action,cancelOnAction;
    @FXML
    private MenuButton categoryMenuButton;
    private final IMovieModel movieModel;
    private final ICategoryModel categoryModel;
    private final MovieController movieController;

    private Movie editableMovie;
    private boolean isEditable = false;

    @Inject
    public CreateMovieController(IMovieModel movieModel, ICategoryModel categoryModel, MovieController controller) {
        this.movieModel = movieModel;
        this.categoryModel = categoryModel;
        this.movieController = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCategorySelection();
        setComponentRules();
        onClickSelectFile.setOnAction(this::selectFileChooser);
        confirm_action.setOnAction(this::movieOnClickAction);
        cancelOnAction.setOnAction(e -> getStage().close()); // sets to close stage on action
    }

    private void selectFileChooser(ActionEvent actionEvent) {
        var chooseFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.mp4,*.mpeg4)", "*.mp4", "*.mpeg4");
        chooseFile.getExtensionFilters().add(extFilter);

        File selectedMovieFile = chooseFile.showOpenDialog(new Stage());
        if (selectedMovieFile != null) {
            path.setText(selectedMovieFile.toURI().toString());
            var media = new Media(selectedMovieFile.toURI().toString().replace("\\", "/"));
            var duration = (int) media.getDuration().toSeconds();
            durationField.setText(duration / 60 + ":" + duration % 60);
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

    private void fillCategorySelection() {
        List<Category> categoryList = tryToGetCategory();
        if(categoryMenuButton.getItems() != null){
            categoryMenuButton.getItems().clear();
            categoryList.stream()
                    .map(category -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(category.name().getValue());

                        return menuItem;
                    })
                    .forEach(menuItem -> categoryMenuButton.getItems().add(menuItem));
        }
    }

    /**
     * tries to get the List of categories
     * @return list of Categories with id,name
     */
    private List<Category> tryToGetCategory() {
        try {
            return categoryModel.getAllCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method that will construct new Movie from user input and tries to create it
     * this is two way purpose and the whole logic depends on the boolean value of editable
     * @param e action event
     */
    private void movieOnClickAction(ActionEvent e) {
        if(!isEditable && isValidatedInput()){
            var collectedCategory = mapSelectedCategories();
            Movie movie = new Movie(
                    0,
                    new SimpleStringProperty(movieName.getText().trim()),
                    personalRatingSpin.getValue(),
                    new SimpleStringProperty(path.getText().trim()),
                    collectedCategory,
                    null);

            int result = tryCreateMovie(movie);
            closeAndUpdate(result,movie.id());
        }else {
            var collectedCategory = mapSelectedCategories();
            Movie movie = new Movie(
                    0,
                    new SimpleStringProperty(movieName.getText().trim()),
                    personalRatingSpin.getValue(),
                    new SimpleStringProperty(path.getText().trim()),
                    collectedCategory,
                    null);

            int result = tryUpdateMovie(movie);
            closeAndUpdate(result,movie.id());
        }
    }

    protected void setEditableView(Movie movie){
        isEditable = true;
        editableMovie = movie;

        movieName.setText(editableMovie.name().getValue());

        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 10.0, editableMovie.rating(),0.5);
        personalRatingSpin.setValueFactory(valueFactory);
        path.setText(editableMovie.absolutePath().getValue());

        if(categoryMenuButton.getItems() != null){
            categoryMenuButton.getItems().clear();
            List<Category> categoryList = tryToGetCategory();
            Set<String> categorySet = editableMovie.categories().stream().map(category -> category.name().getValue()).collect(Collectors.toSet());

            categoryList.stream()
                    .map(category -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(category.name().getValue());

                        if(categorySet.contains(category.name().getValue())){
                            menuItem.setSelected(true);
                        }

                        return menuItem;
                    })
                    .forEach(menuItem -> categoryMenuButton.getItems().add(menuItem));
        }

        confirm_action.setText("Update");
        labelMovieWindow.setText("Update Movie");
    }



    /**
     * validates user input
     * @return false is requirements not met
     */
    private boolean isValidatedInput() {
        boolean isValidated = false;
        if(movieName.getText().isEmpty() || mapSelectedCategories().isEmpty() || path.getText().isEmpty()){
            System.out.println("Please fill all the field! You get the drill" );            // -> notify user that something went wrong
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
        return categoryMenuButton.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem)
                .map(CheckMenuItem.class::cast)
                .filter(CheckMenuItem::isSelected)
                .map(button -> new Category(0,new SimpleStringProperty(button.getText())))
                .toList();

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
            System.out.println("Successfully created movie with id: " + id );
        }else {
            System.out.println("Could not update movie with id: " + id);
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

    private int tryUpdateMovie(Movie movie) {
        try {
            return movieModel.updateMovie(movie);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
