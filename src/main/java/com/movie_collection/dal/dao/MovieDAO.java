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
        long starttime;
        try (Connection con = cm.getConnection()){
            starttime = System.currentTimeMillis();
            String sql = "SELECT M.id, M.name, M.rating, M.path, M.lastview, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Movie M " +
                    "LEFT JOIN CatMovie CM on M.id = CM.movieId " +
                    "LEFT JOIN Category C on C.id = CM.categoryId " +
                    "ORDER BY M.id ASC;";
            PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pstmt.executeQuery();

            // used for adding categories to movie
            while (rs.next()) {
                int id = rs.getInt("id");
                StringProperty name = new SimpleStringProperty(rs.getString("name"));
                double rating = rs.getDouble("rating");
                StringProperty path = new SimpleStringProperty(rs.getString("path"));
                Date lastview = rs.getDate("lastview");

                Movie lastMovie;
                List<Category> categories;
                // check for empty list
                if (movies.isEmpty()) {
                    // add first element
                    String catName = rs.getString("C_name");
                    categories = catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>();
                    movies.add(new Movie(id, name, rating, path, categories, lastview));
                } else {
                    // the last added movie
                    lastMovie = movies.get(movies.size() - 1);
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
        System.out.println("Time to get all the movies: " + (System.currentTimeMillis() - starttime));
        return (movies);
    }

    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        try (Connection con= cm.getConnection()) {
            String sql = "SELECT M.id, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Category C  " +
                    "LEFT JOIN CatMovie CM on C.id = CM.categoryId " +
                    "LEFT JOIN Movie M on M.id = CM.movieId WHERE C.id = ? " +
                    "ORDER BY M.id ASC;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                movies.add(getMovieById(id));
            }
        }
        return movies;
    }

    public Movie getMovieById(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT M.id, M.name, M.rating, M.path, M.lastview, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Movie M  " +
                    "LEFT JOIN CatMovie CM on M.id = CM.movieId " +
                    "LEFT JOIN Category C on C.id = CM.categoryId WHERE M.id = ? " +
                    "ORDER BY M.id ASC, C.id ASC;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            StringProperty name = new SimpleStringProperty(rs.getString("name"));
            double rating = rs.getDouble("rating");
            StringProperty path = new SimpleStringProperty(rs.getString("path"));
            Date lastview = rs.getDate("lastview");
            String catName = rs.getString("C_name");

            ArrayList<Category> allCategories = new ArrayList<>(catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>());
            while (rs.next()) {
                allCategories.add(new Category(rs.getInt("C_id"), new SimpleStringProperty(rs.getString("C_name"))));
            }

            return new Movie(id, name, rating, path, allCategories, lastview);
        }
    }

    public int createMovie(Movie movie) throws SQLException {
        int rowsAffected = 0;
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO Movie name, rating, path, lastview VALUES (?, ?, ?, ?); SELECT SCOPE_IDENTITY() as id ";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().get());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.absolutePath().get());
            pstmt.setDate(4, movie.lastview());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");


            for (Category c : movie.categories()) {
                sql = "INSERT INTO CatMovie (categoryId, movieId) VALUES (?, ?)";
                pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, c.id());
                pstmt.setInt(2, id);
                rowsAffected += pstmt.executeUpdate();
            }
        }
        return rowsAffected + 1;
    }

    public int updateMovie(Movie movie) throws SQLException {
        int rowsAffected = 1;
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Movie SET name = ?, rating = ?, path = ?, lastview = ? WHERE name = (?) SELECT SCOPE_IDENTITY() as id;";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().get());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.absolutePath().get());
            pstmt.setDate(4, movie.lastview());
            pstmt.setString(5, movie.name().get());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");

            sql = "DELETE FROM CatMovie WHERE movieId = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            for (Category c : movie.categories()) {
                System.out.println(c.id());
                sql = "INSERT INTO CatMovie (categoryId, movieId) VALUES (?, ?)";
                pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, c.id());
                pstmt.setInt(2, id);
                pstmt.executeQuery();
                rowsAffected += 1;
            }
        }
        return rowsAffected;
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
