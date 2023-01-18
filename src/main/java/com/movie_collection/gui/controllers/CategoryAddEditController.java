package com.movie_collection.gui.controllers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.bll.helpers.EventType;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.controllers.event.RefreshEvent;
import com.movie_collection.gui.models.ICategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryAddEditController extends RootController implements Initializable {


    @FXML
    private TextField category_name;
    @FXML
    private Button add_category;

    private final ICategoryModel categoryModel;
    private final EventBus eventBus;


    @Inject
    public CategoryAddEditController(ICategoryModel categoryModel, EventBus eventBus) {
        this.categoryModel = categoryModel;
        this.eventBus = eventBus;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add_category.setOnAction(e -> createCategoryOnAction(category_name.getText()));
    }

    /**
     * method that is set on action on the button to manage logic and validation of newly create category
     *
     * @param categoryName that is going to be eventually created
     */
    private void createCategoryOnAction(String categoryName) {
        if (validateLengthAndFill()) {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            var result = tryToCreateCategory(newCategory);

            if (result > 0) {
                AlertHelper.showOptionalAlertWindow("Successfully created category with name :" + categoryName, "", Alert.AlertType.INFORMATION);
                refreshMovieTable();
                getStage().close();
            } else {
                AlertHelper.showOptionalAlertWindow("Could not create category with name :" + categoryName, "", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateLengthAndFill() {
        boolean result = false;
        if (category_name.getText().isEmpty() || category_name.getText().isEmpty()) {
            AlertHelper.showOptionalAlertWindow("Warning: Category cannot be empty. ", "", Alert.AlertType.INFORMATION);
        } else if (category_name.getText().trim().length() < 3) {
            AlertHelper.showOptionalAlertWindow("Warning: Please make it at least 3 chars :) ", "", Alert.AlertType.INFORMATION);
        } else {
            result = true;
        }
        return result;
    }

    /**
     * tries to refresh category pane inside baseController
     */
    private void refreshMovieTable() {
        eventBus.post(new RefreshEvent(EventType.UPDATE_TABLE));
    }

    /**
     * tries to create category with the new name
     *
     * @param newCategory that will be created
     */
    private int tryToCreateCategory(Category newCategory) {
        return categoryModel.createCategory(newCategory);
    }

    @FXML
    public void close_stage(ActionEvent actionEvent) {
        getStage().close();
        actionEvent.consume();
    }
}
