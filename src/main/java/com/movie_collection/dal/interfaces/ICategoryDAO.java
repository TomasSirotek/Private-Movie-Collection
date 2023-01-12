package com.movie_collection.dal.interfaces;

import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICategoryDAO {

    /**
     * Retrieves all Categories from the database and store into a list
     * @return a list of all the Categories from database
     */
    List<Category> getAllCategories() throws SQLException;

    Optional<Category2> getCategoryByName(String categoryName);

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

    // Category2 getCategoryByName(String name) throws SQLException;
}