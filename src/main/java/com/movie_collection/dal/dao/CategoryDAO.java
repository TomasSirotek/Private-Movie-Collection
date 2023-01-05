package com.movie_collection.dal.dao;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.dal.ConnectionManager;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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


    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
        // TODO talk to Patrik to choose wich way is better to call query for joint tables.
        ArrayList<Movie> movies = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT * FROM Movie INNER JOIN CatMovie ON Movie.id = CatMovie.MovieId WHERE CatMovie.CategoryId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                StringProperty title = new SimpleStringProperty(rs.getString("title"));
                double rating = rs.getDouble("imdbRating");
                StringProperty path = new SimpleStringProperty(rs.getString("path"));
                Date lastview = rs.getDate("lastview");
                movies.add(new Movie(id, title, rating, path, lastview));
            }
        }
        return movies;
    }

}
