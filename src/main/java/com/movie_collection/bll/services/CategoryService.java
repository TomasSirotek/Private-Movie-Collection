package com.movie_collection.bll.services;

import com.movie_collection.be.Category;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryService implements ICategoryService {

    @Override
    public List<Category> getAllCategories() {
        return list;
    }

    @Override
    public int createCategory(Category category) {
        list.add(category);
        return 0;
    }

    @Override
    public int deleteCategory(int id) {
        return list.removeIf(x -> x.id() == id);
    }
}
