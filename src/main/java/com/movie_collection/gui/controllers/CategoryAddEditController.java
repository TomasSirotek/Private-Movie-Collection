package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.gui.controllers.abstractController.RootController;
import com.movie_collection.gui.models.CategoryModel;
import com.movie_collection.gui.models.ICategoryModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class CategoryAddEditController extends BaseController implements Initializable {

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
        add_category.setOnAction(e -> {
            try {
                createCategoryOnAction(category_name.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // method that for now is randomly setting ID and creates category, refreshed the pane and closes the stage
    private void createCategoryOnAction(String categoryName) throws SQLException {
        Objects.requireNonNull(categoryName,"Category name cannot be empty");
        Random id = new Random();
        // TODO: just for testing now since we dont have actual dao to get
        Category newCategory = new Category(id.nextInt(),new SimpleStringProperty(categoryName));
        var result =  categoryModel.createCategory(newCategory);
        if(result > 0){
            baseController.refreshScrollPane();
            getStage().close();
        } else {
            // needs to have proper notification call
        }
    }

}
