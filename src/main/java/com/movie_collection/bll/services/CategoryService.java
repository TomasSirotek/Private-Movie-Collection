package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category2;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.dal.interfaces.ICategoryDAO;

import java.util.List;
import java.util.Optional;

public class CategoryService implements ICategoryService {

    private final ICategoryDAO categoryDAO;

    @Inject
    public CategoryService(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category2> getAllCategories() {
        return categoryDAO.getAllCategories();
    }


    @Override
    public int createCategory(Category2 category){
        return categoryDAO.createCategory(category);
    }

    @Override
    public Optional<Category2> getCategoryByName(String categoryName) {
        return categoryDAO.getCategoryByName(categoryName);
    }

    @Override
    public int deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }
}
