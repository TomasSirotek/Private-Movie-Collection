package com.movie_collection.gui.models;

import com.movie_collection.be.Category;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryModel {
    /*
     * abstract method to retrieve all the categories
     * @return Observable list with all its categories objects
     */
    ObservableList<Category> getAllCategories() throws SQLException;

    /**
     * abstract method to createCategory from an object
     * @param category object that will be created
     * @return 0 if no category fails to be  created | 1 if successfully created
     *
     */
    int createCategory(Category category) throws SQLException;

    int deleteCategory(int id) throws SQLException;
}
