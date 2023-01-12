package com.movie_collection.gui.models;

import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryModel {
    /*
     * abstract method to retrieve all the categories
     * @return Observable list with all its categories objects
     */
    ObservableList<Category2> getAllCategories();

    /**
     * abstract method to createCategory from an object
     * @param category object that will be created
     * @return 0 if no category fails to be  created | 1 if successfully created
     *
     */
    int createCategory(Category2 category);

    int deleteCategory(int id);
}
