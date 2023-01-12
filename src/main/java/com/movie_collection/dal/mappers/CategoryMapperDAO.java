package com.movie_collection.dal.mappers;

import com.movie_collection.be.Category2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;


public interface CategoryMapperDAO {

    List<Category2> selectCategories(List<Category2> categories);

    Category2 getCategoryByName(@Param("value")String categoryName);
}
