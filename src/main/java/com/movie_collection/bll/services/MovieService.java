package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.dal.interfaces.IMovieDAO;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieService implements IMovieService {

    private final IMovieDAO movieDAO;

    private final ICategoryDAO categoryDAO;
    private final ICategoryService categoryService;

    @Inject
    public MovieService(IMovieDAO movieDAO,ICategoryDAO categoryDAO, ICategoryService categoryService) {
        this.movieDAO = movieDAO;
        this.categoryDAO = categoryDAO;
        this.categoryService = categoryService;
    }

    @Override
    public List<Movie2> getAllMovies() throws SQLException {
        return movieDAO.getAllMoviesTest();
    }


    @Override
    public int createMovie(Movie2 movie) {
        // -> block to prepare categoriesList
//        List<Category2> category2List = new ArrayList<>();
//
//        for (var category : movie.getCategories()
//             ) {
//            Category2 category2 = new Category2();
//            category2.setName(category.getName());
//            category2List.add(category2);
//        }
//        movie.setCategories(category2List);


        // -> create movie and for each movie add to category

        int result = movieDAO.createMovieTest(movie);
        if(result > 0){
            movie.setId(result);
            for (Category2 category : movie.getCategories()
                 ) {
                Optional<Category2> category2 = categoryDAO.getCategoryByName(category.getName());// -> inject categoryDAO
                if(category2.isPresent()){
                    movieDAO.addCategoryToMovie(category2.get(),movie);
                }
            }
        }

        return result;
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
    public Movie getMovieById(int id) throws SQLException {
        return movieDAO.getMovieById(id);
    }

    @Override
    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
//        return movieDAO.getAllMoviesInTheCategory(categoryId);
        return null;
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
}
