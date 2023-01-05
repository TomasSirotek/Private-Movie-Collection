package com.movie_collection.dal;

import com.movie_collection.be.Movie;

import java.sql.*;

public class MovieDAO {
    private static final ConnectionManager cm = new ConnectionManager();

    public void createMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO Movie (name, rating, path, lastview) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path());
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
            pstmt.setString(1, movie.name());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path());
            pstmt.setDate(4, movie.lastview());
            pstmt.setInt(5, movie.id());
            pstmt.executeUpdate();
        }
    }

    public Movie getMovie(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");
                String path = rs.getString("path");
                Date lastview = rs.getDate("lastview");
                return new Movie(id, name, rating, path, lastview);
            }
        }
        return null;
    }
}
