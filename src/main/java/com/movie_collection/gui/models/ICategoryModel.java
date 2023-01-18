package com.movie_collection.gui.models;

import com.movie_collection.be.Category;
import javafx.collections.ObservableList;

public interface ICategoryModel {
    /*
     * method to retrieve all the categories
     * @return Observable list with all its categories objects
     */
    ObservableList<Category> getAllCategories();

    /**
     * method to createCategory from an object
     *
     * @param category object that will be created
     * @return 0 if no category fails to be  created | 1 if successfully created
     */
    int createCategory(Category category);

    /**
     * method that deleted category by its id
     * @param id of the category to be deleted
     * @return affected rows
     */

    int deleteCategoryById(int id);

}
