package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie2;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.bll.services.interfaces.IAPIService;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.bll.util.IFilter;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.gui.DTO.MovieDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MovieService implements IMovieService {

    private final IMovieDAO movieDAO;
    private final ICategoryService categoryService;
    private final ICategoryDAO categoryDAO; // this should not be here use srvice isntead

    private final IFilter filterUtil;

    private final IAPIService apiService;

    @Inject
    public MovieService(IMovieDAO movieDAO,ICategoryDAO categoryDAO,ICategoryService categoryService,IAPIService apiService,IFilter filterUtil) {
        this.movieDAO = movieDAO;
        this.categoryDAO = categoryDAO;
        this.categoryService = categoryService;
        this.filterUtil = filterUtil;
        this.apiService = apiService;
    }

    @Override
    public List<Movie2> getAllMovies()  {
        return movieDAO.getAllMovies();
    }

    @Override
    public Optional<Movie2> getMovieById(int id)  {
        return Optional.of(movieDAO.getMovieById(id).get()); // still sketchy
    }

    @Override
    public Optional<List<Movie2>> getAllMoviesInTheCategory(int categoryId){
        return Optional.of(movieDAO.getAllMoviesInTheCategoryById(categoryId).get()); // have better check
    }

    @Override
    public int createMovie(Movie2 movie) {
        int result = movieDAO.createMovieTest(movie);
        if(result != 0){
            movie.setId(result);
            getCatAndAdd(movie);
        }
        return result;
    }

    /**
     * TODO : THIS NEEDS TO BE MAPPED INSIDE THE XML !!
     * @param id id of movie to be played
     * @return
     */
    @Override
    public int updateTimeStamp(int id) {
        return movieDAO.updateTimeStamp(id);
    }

    @Override
    public List<Movie> searchMovie(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue) {
        return filterUtil.filteringMovies(listToSearch, query, buttonValue, spinnerValue);
    }

    @Override
    public MovieDTO getMovieByNameAPI(String title) {
        return apiService.getMovieByTitle(title);
    }

    public int updateMovie(Movie2 movie){
        int finalResult = 0; // there is plenty of ways of logic with update categories this one worked for us atm
        Objects.requireNonNull(movie,"Movie cannot be null");

        // tries to update movie
        int resultId = movieDAO.updateMovieById(movie,movie.getId());

        // tries to find and remove the category
        if(resultId > 0){
            finalResult = getCatNameAndRemove(resultId);
        }
        //  tries to assign the new categories again
        if(finalResult > 0){
            getCatAndAdd(movie);
        }
        return finalResult;
    }

    @Override
    public int deleteMovie(int id) {
        return movieDAO.deleteMovieById(id);
    }

    private int getCatNameAndRemove(int resultId) {
        int resultHere = 0;
        Optional<Movie2> fetchedMovie = movieDAO.getMovieById(resultId);
        if(fetchedMovie.isPresent()){
            resultHere = 1;
            int resultRemove = movieDAO.removeCategoryFromMovie(fetchedMovie.get().getId());
            if(resultRemove > 0) {
                AlertHelper.showDefaultAlert("Error: Failed to remove category from movie." + fetchedMovie.get().getId(), Alert.AlertType.ERROR);
            }
        }
        return resultHere;
    }
    /**
     * handles mapping the categories by name and trying to add them to each movie
     * @param movie that will be assigned list of categories
     */
    private void getCatAndAdd(Movie2 movie) {
        Objects.requireNonNull(movie.getCategories(),"Error: Categories cannot be empty for movie with id: "  + movie.getId());

        movie.getCategories().stream()
                .map(category -> categoryDAO.getCategoryByName(category.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(category -> {
                    int addResult = movieDAO.addCategoryToMovie(category, movie);
                    if (addResult <= 0) {
                        AlertHelper.showDefaultAlert("Error: Failed to add category to movie." + category.getId(), Alert.AlertType.ERROR);
                    }
                });
    }
//      movie.getCategories().stream()
//                    .map(category -> categoryDAO.getCategoryByName(category.getName()))
//            .filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .forEach(category -> {
//        int result = movieDAO.addCategoryToMovie(category, movie);
//        if (result <= 0) {
//            // same maybe just loggers for this since its internal implementation should user know ?
//            AlertHelper.showDefaultAlert("Error: Failed to add category to movie." + category.getId(), Alert.AlertType.ERROR);
//        }
//    });
}
