package com.movie_collection.bll.services.interfaces;

import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryService {
    /**
     * abstract method to retrieve all the categories stored in DAL
     * @return List<Category>
     */

    List<Category2> getAllCategories() ;

    /**
     * abstract method to retrieve all the categories stored in DAL
     * @param category
     */

    int createCategory(Category2 category);

    /**
     * abstract method to delete category by its id
     * @param id of the category that will be deleted
     * @return false if not able to delete otherwise return true if successful
     */
    int deleteCategory(int id) ;


}
