package com.movie_collection.dal.dao;

import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie2;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.dal.mappers.CategoryMapperDAO;
import com.movie_collection.dal.mappers.MovieMapperDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.movie_collection.dal.ConnectionManager;
import myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAO implements ICategoryDAO {

    private static final ConnectionManager cm = new ConnectionManager();

    public List<Category> getAllCategories() throws SQLException {
        ArrayList<Category> allCategories = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SELECT id,name FROM Category order By id asc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                StringProperty name = new SimpleStringProperty(resultSet.getString("name"));
                allCategories.add(new Category(id, name));
            }
        }
        return allCategories;
    }

    @Override
    public int addCategory(Category category) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            String sql = "INSERT INTO Category (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.name().getValue());
            return preparedStatement.executeUpdate();
        }
    }
    @Override
    public int deleteCategory(int id) throws SQLException {
        try(Connection connection = cm.getConnection()){
            String sql = "DELETE FROM Category WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }
    @Override
    public Optional<Category2> getCategoryByName(String categoryName)  {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            CategoryMapperDAO mapper = session.getMapper(CategoryMapperDAO.class);
            return Optional.ofNullable(mapper.getCategoryByName(categoryName));
        }
    }

//    public Category2 getCategoryByName(String name) throws SQLException {
//        try(Connection connection = cm.getConnection()){
//            String sql = "SELECT Category.id, Category.name FROM Category WHERE UPPER(Category.name) = UPPER(?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, name);
//            ResultSet rs = preparedStatement.executeQuery();
//            rs.next();
//            // -> needs fix
//            return new Category2(rs.getInt("id"), new SimpleStringProperty(rs.getString("name")));
//        }
//    }
}