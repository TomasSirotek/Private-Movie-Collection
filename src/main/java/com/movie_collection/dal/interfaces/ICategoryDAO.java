package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDAO {

    /**
     * Retrieves all Categories from the database and store into a list
     * @return a list of all the Categories from database
     */
    List<Category> getAllCategories() throws SQLException;

    /**
     * Saves the new information about a new Caterogy into the database
     * @param category the Category to be added
     */
    int addCategory(Category category) throws SQLException;

    /**
     * Deletes the desired Category from the Category database
     * @param Category the Category to be deleted
     */
    /**
     * NEED TO DISCUSS WITH TEAM
     *
     * @return
     */
    int deleteCategory(int id) throws SQLException;

    Category getCategoryByName(String name) throws SQLException;
}