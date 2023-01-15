package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import com.movie_collection.gui.models.ICategoryModel;
import com.movie_collection.gui.models.IMovieModel;
import javafx.collections.FXCollections;
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
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Base controller navigation with switchable content
 * Serves as a root for all the action :)
 * injects the root controller
 */
public class BaseController extends RootController implements Initializable {


    @FXML
    private Spinner<Double> ratingFilterSpinner;
    @FXML
    private Button ratingFilterButton;
    @FXML
    private ScrollPane scroll_pane;
    @FXML
    private StackPane app_content;
    @FXML
    private TextField searchMovies;
    private final IControllerFactory controllerFactory;

    private final IMovieModel movieModel;

    @Inject
    private ICategoryModel categoryModel;

    private CompareSigns currentCompare = CompareSigns.MORE_THAN_OR_EQUAL;

    @Inject
    public BaseController(IControllerFactory controllerFactory,ICategoryModel categoryModel,IMovieModel movieModel) {
        this.controllerFactory = controllerFactory;
        this.categoryModel = categoryModel;
        this.movieModel = movieModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filterBar();
        setupSpinner();
        setCategoriesScrollPane(categoryModel.getAllCategories());
        showMoviesToDelete();

    }

    /**
     * needs docs @patrik
     */
    private void showMoviesToDelete()  {
        var test = movieModel.getAllMovies();
        List<Movie> moviesToDelete = movieModel.getAllMovies().stream()
                .filter(m -> m.getRating() < 6.0 || (m.getLastview() != null && (Instant.now().toEpochMilli() - m.getLastview().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()> 63113852000L)))
                .collect(Collectors.toList());

        String str = "";
        if(!moviesToDelete.isEmpty()){
            for (Movie m:moviesToDelete) {
                str += m.getName() + "\n";
            }
            var alert = AlertHelper.showOptionalAlertWindow("Do you want to delete these movies ? " , str, Alert.AlertType.CONFIRMATION);

            if(alert.isPresent() && alert.get().equals(ButtonType.OK)){
                for (Movie m: moviesToDelete) {
                    movieModel.deleteMovieById(m.getId());
                }
            }
        }
    }

    /**
     * void method that invokes scroll pane to clean content first
     * and then set it back to all categories
     */
    protected void refreshScrollPane() {
        if(scroll_pane != null){
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
        LinkedHashMap<Button, Button> scrollPaneContentMap = allCategories
                .stream()
                .map(category -> {
                    Button categoryBtn = new Button(category.getName());
                    Button deleteBtn = new Button("Delete");

                    // Setting on the action for switching views
                    categoryBtn.setOnAction(event -> {
                        MovieController parent = (MovieController) tryToLoadView();
                        parent.setIsCategoryView(category.getId());
                        switchToView(parent.getView()); // switches into chosen view
                    });
                    categoryBtn.setPrefWidth(140);

                    deleteBtn.setOnAction(event -> {
                        int result = tryToDeleteCategory(category.getId());
                        if (result > 0) {
                            refreshScrollPane();
                        } else {
                            AlertHelper.showDefaultAlert("Could not delete category with id: " + category.getId(), Alert.AlertType.ERROR);
                        }
                    });
                    deleteBtn.setPrefWidth(50);
                    return new AbstractMap.SimpleEntry<>(categoryBtn, deleteBtn);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                // (oldValue, newValue) -> oldValue - a "merge function" that is used to resolve
                // conflicts when two keys are mapped to the same value. In this case, if there is a conflict,
                // the old value is kept and the new value is discarded.

                // LinkedHashmap::new  is a function that creates a new, empty Map object. In this case, a LinkedHashMap is created.
                // collect method is called in order to finish up what we opened at the begging stream

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
            return categoryModel.deleteCategory(id);
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
            movieModel.getAllMovies();
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

    private void filterBar() {
        searchMovies.textProperty().addListener((obs, oldValue, newValue) -> searchMovies());
    }


    @FXML
    private void ratingFilterButtonAction(ActionEvent actionEvent) {
        List<CompareSigns> buttonValues = new ArrayList<>(List.of(CompareSigns.MORE_THAN_OR_EQUAL, CompareSigns.LESS_THAN_OR_EQUAL, CompareSigns.EQUAL));
        int index = buttonValues.indexOf(currentCompare);
        index = index+1 >= buttonValues.size() ? 0 : index + 1;
        currentCompare = buttonValues.get(index);
        ratingFilterButton.setText(currentCompare.getSign());

        searchMovies();
        actionEvent.consume();
    }

    private void setupSpinner(){
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 10.0,1.0, 0.5);
        ratingFilterSpinner.setValueFactory(valueFactory);

        valueFactory.valueProperty().addListener((observable, oldValue, newValue) -> searchMovies());
    }

    private void searchMovies() {
        TableView tableView = (TableView) getStage().getScene().lookup("#moviesTable");
        if (tableView != null) {
            List<Movie> list = movieModel.searchMovies(searchMovies.getText(), currentCompare, ratingFilterSpinner.getValue());
            tableView.getItems().clear();
            tableView.setItems(FXCollections.observableArrayList(list));
        } else {
            System.out.println("The table view could not be found");
        }
    }
}
