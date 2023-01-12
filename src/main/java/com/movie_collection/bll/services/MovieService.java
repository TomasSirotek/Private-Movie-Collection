package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.bll.util.IFilter;
import com.movie_collection.dal.interfaces.IMovieDAO;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieService implements IMovieService {
    private final IMovieDAO movieDAO;
    private final ICategoryService categoryService;

    private final IFilter filterUtil;

    @Inject
    public MovieService(IMovieDAO movieDAO, ICategoryService categoryService, IFilter filter) {
        this.movieDAO = movieDAO;
        this.categoryService = categoryService;
        this.filterUtil = filter;
    }

    @Override
    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    @Override
    public int createMovie(Movie movie) throws SQLException {
        return movieDAO.createMovie(linkingCategoriesToId(movie));
    }

    @Override
    public int updateMovie(Movie movie) throws SQLException {
        return movieDAO.updateMovie(linkingCategoriesToId(movie));
    }

    @Override
    public int deleteMovie(int id) throws SQLException {
        return movieDAO.deleteMovie(id);
    }

    @Override
    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
        return movieDAO.getAllMoviesInTheCategory(categoryId);
    }

    private Movie linkingCategoriesToId(Movie movie) throws SQLException {
        ArrayList<Category> allCategories = new ArrayList<>(categoryService.getAllCategories());
        List<Category> categories = new ArrayList<>(movie.categories());
        for (int i = 0; i < categories.size(); i++) {
            String catName = categories.get(i).name().get();
            Category category = allCategories.stream().filter(c -> c.name().get().equals(catName)).findFirst().orElse(new Category(0, new SimpleStringProperty("Does not exist")));
            categories.set(i, category);
        }
        return new Movie(movie.id(), movie.name(), movie.rating(), movie.absolutePath(), categories, movie.lastview());
    }

    @Override
    public List<Movie> searchMovie(List<Movie> listToSearch, String query, String buttonText, double spinnerValue){
        return filterUtil.filteringMovies(listToSearch, query, buttonText, spinnerValue);
    }
}
