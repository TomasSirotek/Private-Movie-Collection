package com.movie_collection.dal;

import com.movie_collection.be.Movie;

import java.sql.*;
import java.time.ZoneId;

public class MovieDAO {
    private static final ConnectionManager cm = new ConnectionManager();

    public void createMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO Movie (name, rating, path, lastview) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path());
            pstmt.setDate(4, Date.valueOf(movie.lastview().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            pstmt.executeUpdate();
        }
    }
}
