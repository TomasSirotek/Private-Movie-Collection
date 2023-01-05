package com.movie_collection.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.movie_collection.be.Category;
import com.movie_collection.dal.ConnectionManager;
import com.movie_collection.dal.interfaces.ICategoryDAO;

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
                String name = resultSet.getString("name");
                allCategories.add(new Category(id, name));
            }
        }
        return allCategories;
    }


    public void addCategory(Category category) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Category (name) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.name());
            preparedStatement.executeUpdate();
        }
    }


    // TODO Talk to team about the delete category system and binds with tables and info.
        public void deleteCategory(int id) throws SQLException {
        try(Connection connection = cm.getConnection()){
            String sql = "DELETE FROM Category WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }


    /*public List<Movie> getAllMoviesInTheCategory(int categoryId) {
        TODO talk to Patrik to choose wich way is better to call query for joint tables.
    }*/

}
