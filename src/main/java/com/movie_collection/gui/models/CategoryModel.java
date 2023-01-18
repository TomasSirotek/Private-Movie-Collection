package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CategoryModel implements ICategoryModel {

    private final ICategoryService categoryService;

    @Inject
    public CategoryModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ObservableList<Category> getAllCategories() {
        return FXCollections.observableArrayList(
                categoryService.getAllCategories());
    }

    @Override
    public int createCategory(Category category) {
        return categoryService.createCategory(category);
    }

    @Override
    public int deleteCategoryById(int id) {
        return categoryService.deleteCategory(id);
    }


}
