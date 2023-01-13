package com.movie_collection.gui.models;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.gui.DTO.MovieDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieModel implements  IMovieModel{

    private final IMovieService movieService;

    private final ObservableList<Movie> allMovies;
    private final ObservableList<Movie> filteredMovies;

    @Inject
    public MovieModel(IMovieService movieService) throws SQLException {
        this.movieService = movieService;
        this.filteredMovies = FXCollections.observableArrayList();
        this.allMovies = FXCollections.observableArrayList();
        getAllMovies();
        filteredMovies.setAll(allMovies);

    }

    @Override
    public ObservableList<Movie2> getAllMovies() {
        return FXCollections.observableArrayList(
                movieService.getAllMovies()
        );
//        var movie = movieService.getAllMovies();
//        var test = movie.stream()
//                .map(m -> {
//                    List<Category> movieCategoriesList = m.getCategories().stream()
//                            .map(c -> new Category(c.getId(), new SimpleStringProperty(c.getName())))
//                            .collect(Collectors.toList());
//                    return new Movie(m.getId(), new SimpleStringProperty(m.getName()), m.getRating(), new SimpleStringProperty(m.getAbsolutePath()), m.getLastview(), movieCategoriesList);
//                }).toList();
//        allMovies.setAll(FXCollections.observableArrayList(test));
//        filteredMovies.setAll(allMovies);

       // return test; // so we only return here
    }

    @Override
    public ObservableList<Optional<List<Movie2>>> getAllMoviesInTheCategory(int categoryId) {

//        allMovies.setAll(FXCollections.observableArrayList(movieService.getAllMoviesInTheCategory(categoryId)));
//        filteredMovies.setAll(allMovies); // but we never retur nhere ?
//        return null;

        return FXCollections.observableArrayList(
                movieService.getAllMoviesInTheCategory(categoryId)
        );
    }

    @Override
    public ObservableList<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    @Override
    public int createMovie(Movie2 movie) {
        return movieService.createMovie(movie);
    }
    @Override
    public int updateMovie(Movie2 movie) {
        return movieService.updateMovie(movie);
    }
    @Override
    public int deleteMovieById(int id) {
        return movieService.deleteMovie(id);
    }

    @Override
    public int updateTimeStamp(int id) {
        return movieService.updateTimeStamp(id);
    }

    @Override
    public MovieDTO findMovieByNameAPI(String title) {
        return movieService.getMovieByNameAPI(title);
    }

    @Override
    public void searchMovies(String query, CompareSigns buttonValue, double spinnerValue) {
        filteredMovies.setAll(movieService.searchMovie(allMovies, query, buttonValue, spinnerValue));
    }
}
