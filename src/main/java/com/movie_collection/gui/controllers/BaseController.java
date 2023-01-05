package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Base controller navigation with switchable content
 * Serves as a root for all the action :)
 * injects the root controller
 */
public class BaseController extends RootController {

    @FXML
    private StackPane app_content;

    @Inject
    IControllerFactory rootController;

    @Inject
    public BaseController(IControllerFactory rootController) {
        this.rootController = rootController;
    }

    /**
     * method bound with fxml that loads the parent and shows new scene
     * @param actionEvent event
     */
    @FXML
    private void onActionAddMovie(ActionEvent actionEvent) throws IOException {
        Parent parent = loadNodesView(ViewType.CREATE_EDIT);
        show(parent,"Add new Movie");
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
        Parent parent = loadNodesView(ViewType.MOVIES);
        switchToView(parent);
        e.consume();
    }

    /**
     * method for loading fxml file from root controller
     * @param viewType enum specifying needed view
     * @return parent loaded from factory
     */

    private Parent loadNodesView(ViewType viewType) throws IOException {
        return rootController.loadFxmlFile(viewType).getView();
    }

    /**
     * method to clear current app_content and replaces it with new parent
     * @param parent that will be inserted inside the stack pane
     */
    private void switchToView(Parent parent){
        app_content.getChildren().clear();
        app_content.getChildren().add(parent);
    }

}
