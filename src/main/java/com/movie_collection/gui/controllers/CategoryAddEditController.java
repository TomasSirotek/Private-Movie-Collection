package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.models.CategoryModel;
import com.movie_collection.gui.models.ICategoryModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class CategoryAddEditController extends RootController implements Initializable {

    @FXML
    private TextField category_name;
    @FXML
    private Button add_category;
    private final ICategoryModel categoryModel;

    private final BaseController baseController;

    @Inject
    public CategoryAddEditController(ICategoryModel categoryModel,BaseController baseController) {
        this.categoryModel = categoryModel;
        this.baseController = baseController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add_category.setOnAction(e -> createCategoryOnAction(category_name.getText()));
    }

    /**
     * method that is set on action on the button to manage logic and validation of newly create category
     * @param categoryName that is going to be eventualy created
     */
    private void createCategoryOnAction(String categoryName) {
        Objects.requireNonNull(categoryName,"Category name cannot be empty");
        Category2 newCategory = new Category2();
        newCategory.setName(categoryName);
        var result = tryToCreateCategory(newCategory);

        if(result > 0){
            AlertHelper.showOptionalAlertWindow("Successfully created category with name :" + categoryName, Alert.AlertType.INFORMATION);
            refreshMovieTable();
            getStage().close();
        } else {
            AlertHelper.showOptionalAlertWindow("Could not create category with name :" + categoryName, Alert.AlertType.ERROR);
        }
    }

    /**
     * tries to refresh category pane inside baseController
     */
    private void refreshMovieTable() {
        try {
            baseController.refreshScrollPane();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * tries to create category with the new name
     * @param newCategory that will be created

     */
    private int tryToCreateCategory(Category2 newCategory) {
        return categoryModel.createCategory(newCategory);
    }

}
