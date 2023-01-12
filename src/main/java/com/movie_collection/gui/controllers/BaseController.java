package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import com.movie_collection.gui.models.ICategoryModel;
import com.movie_collection.gui.models.IMovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Base controller navigation with switchable content
 * Serves as a root for all the action :)
 * injects the root controller
 */
public class BaseController extends RootController implements Initializable {

    @FXML
    private ScrollPane scroll_pane;
    @FXML
    private StackPane app_content;

    private IControllerFactory controllerFactory;

    private ICategoryModel categoryModel;
    @Inject
    private IMovieModel movieModel;

    @Inject
    public BaseController(IControllerFactory controllerFactory, ICategoryModel categoryModel, IMovieModel movieModel) {
        this.controllerFactory = controllerFactory;
        this.categoryModel = categoryModel;
        this.movieModel = movieModel;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setCategoriesScrollPane(categoryModel.getAllCategories());
            showMoviesToDelete();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMoviesToDelete() throws SQLException {
        List<Movie> allMovies = movieModel.getAllMovies();
        List<Movie> moviesToDelete = allMovies.stream()
                .filter(m -> m.rating() < 6.0 || (m.lastview() != null && (Instant.now().toEpochMilli() - m.lastview().getTime() > 63113852000L))) // add movie if rating is < 6 or if lastview is not null and Current time - (lastview time is more than time of 2 years in miliseconds)
                .toList();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Do you want to delete these movies ?");
        String str = "";
        for (Movie m:moviesToDelete) {
            str += m.name().get() + "\n";
        }
        alert.setContentText(str);
        alert.getButtonTypes().setAll(new ButtonType("Delete"), new ButtonType("Cancel"));
        Optional<ButtonType> btn= alert.showAndWait();
        String button = btn.isPresent() ? btn.get().getText() : "Cancel";
        if (button.equals("Delete")){
            for (Movie m: moviesToDelete) {
                movieModel.deleteMovie(m.id());
            }
        }
    }

    /**
     * void method that invokes scroll pane to clean content first
     * and then set it back to all categories
     */
    protected void refreshScrollPane() throws SQLException {
        if(scroll_pane.getContent() != null){
            scroll_pane.setContent(null);
            setCategoriesScrollPane(categoryModel.getAllCategories());
        }
    }

    /**
     * method to set all the categories from List<Category> that uses Linked hashmap to store two buttons that will be then
     * one by one added into the empty scroll pane
     * if no categories are provided stack pane is filled with Label that notifies the user about not having any category listed yet
     * LinkedHashMap is added here due to the reason that whenever new Button is added it is automatickly put as the last and keps the order
     * TODO: Maybe all of the body can be exctracted into separated class since it look so hoooge
     * @param allCategories list of all categories
     */
    private void setCategoriesScrollPane(List<Category> allCategories) {
        // This code is creating a new Map object that is populated with the key-value pairs of a given Map,
        //  and then returning it.
        LinkedHashMap<Button, Button> scrollPaneContentMap = allCategories.stream()
                .map(category -> {
                    Button categoryBtn = new Button(category.name().getValue());
                    Button deleteBtn = new Button("Delete");

                    // Setting on the action for switching views
                    categoryBtn.setOnAction(event -> {
                        MovieController parent = (MovieController) tryToLoadView();
                        parent.setIsCategoryView(category.id());
                        switchToView(parent.getView()); // switches into chosen view
                    });
                    categoryBtn.setPrefWidth(140);

                    deleteBtn.setOnAction(event -> {
                        int result = tryToDeleteCategory(category.id());
                        if (result > 0) {
                            try {
                                refreshScrollPane();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            throw new RuntimeException("Could not delete category with id: " + category.id()); // TODO: Fix to have better handeling
                        }
                    });
                    deleteBtn.setPrefWidth(50);
                    return new AbstractMap.SimpleEntry<>(categoryBtn, deleteBtn);
                })
                // (oldValue, newValue) -> oldValue - a "merge function" that is used to resolve
                // conflicts when two keys are mapped to the same value. In this case, if there is a conflict,
                // the old value is kept and the new value is discarded.

                // LinkedHashmap::new  is a function that creates a new, empty Map object. In this case, a LinkedHashMap is created.
                // collect method is called in order to finish up what we opened at the begging stream
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        if (scrollPaneContentMap.isEmpty()) {
            scroll_pane.setContent(new Label("Empty")); // sets content if no buttons are filled
        } else {
            VBox vBox = new VBox(); // creates new VBox and HBox in order to go <CategoryTitle> and <DeleteButton>
            for (Map.Entry<Button, Button> entry : scrollPaneContentMap.entrySet()) {
                HBox hBox = new HBox(); // creates new HBox
                hBox.getChildren().add(entry.getKey()); // add  button Key
                hBox.getChildren().add(entry.getValue()); // add button value
                vBox.getChildren().add(hBox); // sets the vbox to hold HBox
            }
            scroll_pane.setContent(vBox); // finally sets the content into the scroll pane
        }
    }

    /**
     * method that tries to delete category by id
     * @param id
     * @return for now int that must be > 0 in order to successfully delete it
     */
    private int tryToDeleteCategory(int id) {
        try {
            return categoryModel.deleteCategory(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method that tries to load the view
     * @return parent that will be loaded
     */
    private RootController tryToLoadView() {
        try {
            return loadNodesView(ViewType.MOVIES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method bound with fxml that loads the parent and shows new scene
     * @param actionEvent event
     */
    @FXML
    private void onActionAddMovie(ActionEvent actionEvent) throws IOException {
        RootController parent = loadNodesView(ViewType.CREATE_EDIT);
        show(parent.getView(),"Add new Movie");
        actionEvent.consume();
    }

    /**
     * method to invoke action to Choose Media Player Path
     * @param actionEvent event
     */
    @FXML
    private void onActionSelectMedia(ActionEvent actionEvent) throws IOException {
        RootController parent = loadNodesView(ViewType.MEDIA_PLAYER_SELECTION);
        show(parent.getView(),"Select Media Player Path");
        actionEvent.consume();
    }

    /**
     * private method for showing new stages whenever its need
     * @param parent root that will be set
     * @param title title for new stage
     */
    private void show(Parent parent, String title) {
        Stage stage = new Stage();
        Scene scene = new Scene(parent);

        stage.initOwner(getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(title);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method bound with .fxml file and loads parent depending on the enum and switches the inner content
     * @param e event
     * @throws IOException if not able to read file
     */
    @FXML
    private void clickMovies(ActionEvent e) throws IOException {
        RootController parent = loadNodesView(ViewType.MOVIES);
        switchToView(parent.getView());
        e.consume();
    }

    /**
     * method for loading fxml file from root controller
     * @param viewType enum specifying needed view
     * @return parent loaded from factory
     */

    private RootController loadNodesView(ViewType viewType) throws IOException {
        return controllerFactory.loadFxmlFile(viewType);
    }

    /**
     * method to clear current app_content and replaces it with new parent
     * @param parent that will be inserted inside the stack pane
     */
    private void switchToView(Parent parent){
        app_content.getChildren().clear();
        app_content.getChildren().add(parent);
    }


    /**
     * method to invoke action to add category
     * @param actionEvent event
     */
    @FXML
    private void onActionAddCategory(ActionEvent actionEvent) throws IOException {
        RootController parent = loadNodesView(ViewType.CATEGORY_ADD_EDIT);
        show(parent.getView(),"Add new Category");
        actionEvent.consume();
    }



    /** TODO:
     * This method does nothing for now it is just prepared for later extension
     * depens on what ever we going to use it for it can be deleted or it
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onActionGoHome(ActionEvent actionEvent) throws IOException {
        RootController parent = loadNodesView(ViewType.MOVIES);
        switchToView(parent.getView());
        actionEvent.consume();
    }
}
