package com.movie_collection.dal.mappers;

import com.movie_collection.be.Category2;
import org.apache.ibatis.annotations.Param;

import java.util.List;



public interface CategoryMapperDAO {

    List<Category2> getAllCategories();

    Category2 getCategoryByName(@Param("value")String categoryName);

    int createCategory(Category2 category2);

    int deleteCategory(@Param("catId") int id);
}
