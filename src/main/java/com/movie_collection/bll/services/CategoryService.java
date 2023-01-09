package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.dal.interfaces.ICategoryDAO;

import java.sql.SQLException;
import java.util.List;

public class CategoryService implements ICategoryService {

    private final ICategoryDAO categoryDAO;

    @Inject
    public CategoryService(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    @Override
    public int createCategory(Category category) throws SQLException {
        return categoryDAO.addCategory(category);
    }

    @Override
    public int deleteCategory(int id) throws SQLException {
        return categoryDAO.deleteCategory(id);
    }
}
