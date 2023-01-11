package com.movie_collection.dal.mappers;

import com.movie_collection.be.Category2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapperDAO {
    @Select("SELECT id,name FROM Category")
    List<Category2> selectCategories(List<Category2> categories);
}
