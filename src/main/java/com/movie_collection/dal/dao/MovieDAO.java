package com.movie_collection.dal.dao;

import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie2;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.dal.mappers.MovieMapperDAO;
import myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO implements IMovieDAO {

    Logger logger = LoggerFactory.getLogger(MovieDAO.class);

    @Override
    public List<Movie2> getAllMovies() {
        List<Movie2> allMovies = new ArrayList<>();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            allMovies = mapper.getAllMovies();
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return allMovies;
    }

    @Override
    public Optional<Movie2> getMovieById(int id) {
        Movie2 movie2 = new Movie2();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            movie2 = mapper.getMovieById(id);
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return Optional.ofNullable(movie2);
    }

    @Override
    public Optional<List<Movie2>> getAllMoviesInTheCategoryById(int categoryId) {
        List<Movie2> fetchedMovieInRole = new ArrayList<>();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            fetchedMovieInRole = mapper.getAllMoviesByCategoryId(categoryId);
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return Optional.ofNullable(fetchedMovieInRole);
    }

    @Override
    public int createMovieTest(Movie2 movie) {
        int returnedId = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.createMovieTest(movie);
            session.commit();//  after commit if rows > 0 returns the generated key
            returnedId = affectedRows > 0 ? movie.getId() : 0;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return returnedId;
    }

    @Override
    public int updateMovieById(Movie2 movie, int id) {
        int resultId = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.updateMovie(movie.getName(), movie.getRating(), movie.getAbsolutePath(), movie.getId());
            session.commit();//  after commit if rows > 0 returns the key
            resultId = affectedRows == -1 ? movie.getId() : 0;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return resultId;
    }

    @Override
    public int addCategoryToMovie(Category2 category2, Movie2 movie2) {
        int finalAffectedRows = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.addCategoryToMovie(category2.getId(), movie2.getId());
            session.commit();
            return affectedRows;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return finalAffectedRows;
    }

    @Override
    public int removeCategoryFromMovie(int id) {
        int finalAffectedRows = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.removeCategoryMovie(id);
            session.commit();
            return affectedRows;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return finalAffectedRows;
    }

    @Override
    public int deleteMovieById(int id) {
        int finalAffectedRows = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.deleteMovieById(id);
            session.commit(); // end a unit of work -> if u want to do more open new session -> ensure no leftovers
            return affectedRows;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return finalAffectedRows;
    }

    @Override
    // this needs to be updated and fixed
    public int updateTimeStamp(int id) {
        int finalAffectedRows = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Timestamp ts = Timestamp.from(Instant.now());
        String date = dateFormat.format(ts);
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            // int affectedRows = mapper.updateTimeStamp(id);
            session.commit(); // end a unit of work -> if u want to do more open new session -> ensure no leftovers
            // return affectedRows;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return finalAffectedRows;
    }


//        String sql = "UPDATE Movie SET lastview = '"+ date +"'" + "WHERE id='"+id+"'";


}
