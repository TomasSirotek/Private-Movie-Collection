package com.movie_collection.dal.dao;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;
import com.movie_collection.dal.ConnectionManager;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.dal.mappers.MovieMapperDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO implements IMovieDAO {

    @Override
    public List<Movie2> getAllMoviesTest(){
        List<Movie2> allMovies;
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            allMovies = mapper.getAllMovies();
        }
        return allMovies;
    }

    @Override
    public Optional<Movie2> getMovieById(int id)  {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            return Optional.ofNullable(mapper.getMovieById(id));
        }
    }

    @Override
    public Optional<List<Movie2>> getAllMoviesInTheCategoryTest(int categoryId)  {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            return Optional.ofNullable(mapper.getAllMoviesByCategoryId(categoryId));
        }
    }

    @Override
    public int createMovieTest(Movie2 movie){
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.createMovieTest(movie);
            session.commit();//  after commit if rows > 0 returns the generated key
            return affectedRows > 0 ? movie.getId() : 0;
        }
    }

    @Override
    public int updateMovie(Movie2 movie,int id) {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.updateMovie(movie.getName(),movie.getRating(),movie.getAbsolutePath(),movie.getId());
            session.commit();//  after commit if rows > 0 returns the key
            return affectedRows == -1 ? movie.getId() : 0;
        }
    }
    @Override
    public int addCategoryToMovie(Category2 category2,Movie2 movie2){
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.addCategoryToMovie(category2.getId(),movie2.getId());
            session.commit();
            return affectedRows;
        }
    }

    @Override
    public int removeCategoryFromMovie(int id) {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.removeCategoryMovie(id);
            session.commit();
            return affectedRows;
        }
    }




    @Override
    public int deleteMovie(int id){
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.deleteMovieById(id);
            session.commit(); // end a unit of work -> if u want to do more open new session -> ensure no leftovers
            return affectedRows;
        }
    }
}
