package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    /**
     * Retrieves all Categories from the database and store into a list
     *
     * @return a list of all the Categories from database
     */
    List<Category> getAllCategories();

    /**
     * Saves the new information about a new Category into the database
     *
     * @param category the Category to be added
     */
    int createCategory(Category category);

    Optional<Category> getCategoryByName(String categoryName);
    /**
     * Deletes the desired Category from the Category database
     *
     * @param id which by the category will be deleted from database
     * @return 0 if no rows affected otherwise return 1 as success
     */
    int deleteCategory(int id);


}
