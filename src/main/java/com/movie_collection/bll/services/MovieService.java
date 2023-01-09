package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.dal.interfaces.IMovieDAO;

import java.sql.SQLException;
import java.util.List;

public class MovieService implements IMovieService {
    private final IMovieDAO movieDAO;
    private final ICategoryService categoryService;

    @Inject
    public MovieService(IMovieDAO movieDAO, ICategoryService categoryService) {
        this.movieDAO = movieDAO;
        this.categoryService = categoryService;
    }

    @Override
    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    @Override
    public int createMovie(Movie movie) throws SQLException {
        List<Category> categories = movie.categories();
        for (int i = 0; i < categories.size(); i++) {
            String catName = categories.get(i).name().get();
            Category category = categoryService.getCategoryByName(catName);
            categories.set(i, category);
        }
        return movieDAO.createMovie(new Movie(movie.id(), movie.name(), movie.rating(), movie.absolutePath(), categories, movie.lastview()));
    }

    @Override
    public int updateMovie(Movie movie) throws SQLException {
        return movieDAO.updateMovie(movie);
    }

    @Override
    public int deleteMovie(int id) throws SQLException {
        return movieDAO.deleteMovie(id);
    }

    @Override
    public Movie getMovieById(int id) throws SQLException {
        return movieDAO.getMovieById(id);
    }

    @Override
    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
        return movieDAO.getAllMoviesInTheCategory(categoryId);
    }
}
