package com.movie_collection.dal.interfaces;

import java.util.List;

public interface ICategoryDAO {

    /**
     * Retrieves all Categories from the database and store into a list
     * @return a list of all the Categories from database
     */
    //List<Category> getAllCategories();

    /**
     * Saves the new information about a new Caterogy into the database
     * @param Category the Category to be added
     */
    //void addCategory(Category category) throws SQLException;

    /**
     * Deletes the desired Category from the Category database
     * @param Category the Category to be deleted
     */
    /**
     * NEED TO DISCUSS WITH TEAM
     */
    //void deleteCategory(Category category);

    /**
     * Retrieves all movies in a genre/category in specific
     * @param categoryId the id of a category we're getting the movies from
     * @return a list of all movies in a genre/category.
     */
    //List<Category> getAllMoviesInTheCategory(int categoryId);
}
