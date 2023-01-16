package com.movie_collection.dal.dao;

import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.dal.mappers.MovieMapperDAO;
import myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO implements IMovieDAO {

    Logger logger = LoggerFactory.getLogger(MovieDAO.class);

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            allMovies = mapper.getAllMovies();
        } catch (Exception ex) {
            //throw new SqlSessionException("An error occurred mapping tables",ex);
             logger.error("An error occurred mapping tables", ex);
        }
        return allMovies;
    }

    @Override
    public Optional<Movie> getMovieById(int id) {
        Movie movie = new Movie();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            movie = mapper.getMovieById(id);
        } catch (Exception ex) {
          //  throw new SqlSessionException("An error occurred mapping tables",ex);
            logger.error("An error occurred mapping tables", ex);
        }
        return Optional.ofNullable(movie);
    }

    @Override
    public Optional<List<Movie>> getAllMoviesInTheCategoryById(int categoryId) {
        List<Movie> fetchedMovieInRole = new ArrayList<>();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            fetchedMovieInRole = mapper.getAllMoviesByCategoryId(categoryId);
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return Optional.ofNullable(fetchedMovieInRole);
    }

    @Override
    public int createMovieTest(Movie movie) {
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
    public int updateMovieById(Movie movie) {
        int resultId = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.updateMovie(movie);
            session.commit();//  after commit if rows > 0 returns the key
            resultId = affectedRows == -1 ? movie.getId() : 0;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return resultId;
    }

    @Override
    public int addCategoryToMovie(Category category, Movie movie) {
        int finalAffectedRows = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.addCategoryToMovie(category.getId(), movie.getId());
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
    public int updateTimeStamp(String date,int id) {
        int finalAffectedRows = 0;
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
             int affectedRows = mapper.updateTimeStamp(date,id);
            session.commit(); // end a unit of work -> if u want to do more open new session -> ensure no leftovers
            return affectedRows;
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return finalAffectedRows;
    }


}
