package com.movie_collection.dal.dao;

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

    public void createMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO Movie (name, rating, path, lastview) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().get());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path().get());
            pstmt.setDate(4, movie.lastview());
            pstmt.executeUpdate();
        }
    }

    public void deleteMovie(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void updateMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Movie SET name = ?, rating = ?, path = ?, lastview = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().toString());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path().toString());
            pstmt.setDate(4, movie.lastview());
            pstmt.setInt(5, movie.id());
            pstmt.executeUpdate();
        }
    }

    public Movie getMovieById(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            StringProperty name = new SimpleStringProperty(rs.getString("name"));
            double rating = rs.getDouble("rating");
            StringProperty path = new SimpleStringProperty(rs.getString("path"));
            Date lastview = rs.getDate("lastview");
            return new Movie(id, name, rating, path, lastview);
        }
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection con = cm.getConnection()){
            String sql = "SELECT * FROM Movie";
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                StringProperty name = new SimpleStringProperty(rs.getString("name"));
                double rating = rs.getDouble("rating");
                StringProperty path = new SimpleStringProperty(rs.getString("path"));
                Date lastview = rs.getDate("lastview");
                movies.add(new Movie(id, name, rating, path, lastview));
            }
        }
        return (movies);
    }
}
