package com.movie_collection.bll.services;

import com.movie_collection.be.Category;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryService implements ICategoryService {

    Category[] categories = {
            new Category(1, new SimpleStringProperty("Action")),
            new Category(2, new SimpleStringProperty("Horror"))
    };
    List<Category> list = new ArrayList<>(Arrays.asList(categories));


    @Override
    public List<Category> getAllCategories() {
        return list;
    }

    @Override
    public int createCategory(Category category) {
        System.out.println(category.toString());
        list.add(category);
        return 0;
    }

    @Override
    public boolean deleteCategory(int id) {
        return list.removeIf(x -> x.id() == id);
    }
}
