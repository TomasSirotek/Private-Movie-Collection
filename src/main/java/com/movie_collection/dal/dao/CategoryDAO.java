package com.movie_collection.dal.dao;


import com.movie_collection.be.Category2;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.dal.mappers.CategoryMapperDAO;
import myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAO implements ICategoryDAO {

    @Override
    public List<Category2> getAllCategories(){
        List<Category2> allCategories = new ArrayList<>();
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            CategoryMapperDAO mapper = session.getMapper(CategoryMapperDAO.class);
            allCategories = mapper.getAllCategories();
        }
        return allCategories;
    }

    @Override
    public Optional<Category2> getCategoryByName(String categoryName) {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            CategoryMapperDAO mapper = session.getMapper(CategoryMapperDAO.class);
            return Optional.ofNullable(mapper.getCategoryByName(categoryName));
        }
    }

    @Override
    public int addCategory(Category2 category) {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            CategoryMapperDAO mapper = session.getMapper(CategoryMapperDAO.class);
            int affectedRows = mapper.createCategory(category);
            session.commit();//  after commit if rows > 0 returns the generated key
            return affectedRows > 0 ? category.getId() : 0;
        }
    }
    @Override
    public int deleteCategory(int id) {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            CategoryMapperDAO mapper = session.getMapper(CategoryMapperDAO.class);
            int result = mapper.deleteCategory(id);
            session.commit();
            return result;
        }
    }

}