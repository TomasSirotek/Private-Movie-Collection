package com.movie_collection.dal.dao;

import com.movie_collection.be.Category;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.movie_collection.dal.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO {

    private static final ConnectionManager cm = new ConnectionManager();

    public List<Category> getAllCategories() throws SQLException {
        ArrayList<Category> allCategories = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM ALL_PLAYLISTS order By id asc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                StringProperty name = new SimpleStringProperty("name");
                allCategories.add(new Category(id, name));
            }
        }
        return allCategories;
    }

    //Return   integer from addCat
    public void addCategory(Category category) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Category (name) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.name().get());
            preparedStatement.executeUpdate();
        }
    }
    public int deleteCategory(int id) throws SQLException {
        try(Connection connection = cm.getConnection()){
            String sql = "DELETE FROM Category WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int rs = preparedStatement.executeUpdate();
            return rs;
        }
    }
}