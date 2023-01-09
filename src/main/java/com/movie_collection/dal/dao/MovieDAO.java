package com.movie_collection.dal.dao;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.dal.ConnectionManager;
import com.movie_collection.dal.interfaces.IMovieDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDAO {
    private static final ConnectionManager cm = new ConnectionManager();

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection con = cm.getConnection()){
            String sql = "SELECT M.id, M.name, M.rating, M.path, M.lastview, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Movie M " +
                    "LEFT JOIN CatMovie CM on M.id = CM.movieId " +
                    "LEFT JOIN Category C on C.id = CM.categoryId " +
                    "ORDER BY M.id ASC;";
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet rs = pstmt.executeQuery();

            // used for adding categories to movie
            while (rs.next()) {
                int id = rs.getInt("id");
                StringProperty name = new SimpleStringProperty(rs.getString("name"));
                double rating = rs.getDouble("rating");
                StringProperty path = new SimpleStringProperty(rs.getString("path"));
                Date lastview = rs.getDate("lastview");

                Movie lastMovie;
                // check for empty list
                if (movies.isEmpty()) {
                    // add first element
                    String catName = rs.getString("C_name");
                    List<Category> categories = catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>();
                    movies.add(new Movie(id, name, rating, path, categories, lastview));
                } else {
                    // the last added movie
                    lastMovie = movies.get(movies.size() - 1);
                    List<Category> categories;
                    // if it is the same as movie in this row
                    if (lastMovie.id() == id) {
                        // add the extra category
                        categories = new ArrayList<>(lastMovie.categories());
                        categories.add(new Category(rs.getInt("C_id"), new SimpleStringProperty(rs.getString("C_name"))));
                        // remove the old movie
                        movies.remove(lastMovie);
                    } else {
                        String catName = rs.getString("C_name");
                        categories = catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>();
                    }
                    movies.add(new Movie(id, name, rating, path, new ArrayList<>(categories), lastview));
                }
            }
        }
        return (movies);
    }

    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        try (Connection con= cm.getConnection()) {
            String sql = "SELECT Movie.id, Movie.title, Movie.rating, Movie.path, Movie.lastview, CatMovie.MovieId, CatMovie.CategoryId FROM Movie " +
                            "INNER JOIN CatMovie ON Movie.id = CatMovie.MovieId WHERE CatMovie.CategoryId = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                StringProperty title = new SimpleStringProperty(rs.getString("title"));
                double rating = rs.getDouble("rating");
                StringProperty path = new SimpleStringProperty(rs.getString("path"));
                Date lastview = rs.getDate("lastview");
                movies.add(new Movie(id, title, rating, path, getCategoriesOfMovie(id, con), lastview));
            }
        }
        return movies;
    }

    public Movie getMovieById(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT id, name, rating, path, lastview FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            StringProperty name = new SimpleStringProperty(rs.getString("name"));
            double rating = rs.getDouble("rating");
            StringProperty path = new SimpleStringProperty(rs.getString("path"));
            Date lastview = rs.getDate("lastview");
            return new Movie(id, name, rating, path, getCategoriesOfMovie(id, con), lastview);
        }
    }

    private List<Category> getCategoriesOfMovie(int MovieId, Connection con) throws SQLException {
        ArrayList<Category> allCategories = new ArrayList<>();
        String sql = "SELECT Category.id, Category.name, CatMovie.CategoryId, CatMovie.MovieId FROM Category JOIN CatMovie ON Category.id = CatMovie.CategoryId WHERE MovieId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, MovieId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            StringProperty name = new SimpleStringProperty(rs.getString("name"));
            allCategories.add(new Category(id, name));
        }
        return allCategories;
    }

    public int createMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO Movie name, rating, path, lastview VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().get());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.absolutePath().get());
            pstmt.setDate(4, movie.lastview());
            return pstmt.executeUpdate();
        }
    }

    public int updateMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Movie SET name = ?, rating = ?, path = ?, lastview = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().get());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.absolutePath().get());
            pstmt.setDate(4, movie.lastview());
            pstmt.setInt(5, movie.id());
            return pstmt.executeUpdate();
        }
    }

    public int deleteMovie(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM CatMovie WHERE Movieid = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rs1 = pstmt.executeUpdate();
            String sql2 = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, id);
            int rs2 = pstmt2.executeUpdate();
            return rs1 + rs2;
        }
    }
}
