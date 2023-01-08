package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryModel implements ICategoryModel {

    private final ICategoryService categoryService;

    private ObservableList<Category> categories;

    @Inject
    public CategoryModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ObservableList<Category> getAllCategories(){
        return categories = FXCollections.observableArrayList(
                categoryService.getAllCategories()
        );
    }

    @Override
    public int createCategory(Category category) {
         return categoryService.createCategory(category);
    }

    @Override
    public int deleteCategory(int id) {
        return categoryService.deleteCategory(id);
    }
}
