package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.CompareSigns;
import com.movie_collection.bll.helpers.DateFormat;
import com.movie_collection.bll.services.interfaces.IAPIService;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.bll.util.IFilter;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.gui.DTO.MovieDTO;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MovieService implements IMovieService {

    private final IMovieDAO movieDAO;
    private final ICategoryService categoryService;

    private final IFilter filterUtil;

    private final IAPIService apiService;
    private static String playerPath;
    private final static String MEDIA_PLAYER_PATH = "mediaPlayerPath.txt";

    @Inject
    public MovieService(IMovieDAO movieDAO, ICategoryService categoryService, IAPIService apiService, IFilter filterUtil) {
        this.movieDAO = movieDAO;
        this.categoryService = categoryService;
        this.filterUtil = filterUtil;
        this.apiService = apiService;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    @Override
    public Optional<Movie> getMovieById(int id) {
        return Optional.of(movieDAO.getMovieById(id).get());
    }

    @Override
    public Optional<List<Movie>> getAllMoviesInTheCategory(int categoryId) {
        return Optional.of(movieDAO.getAllMoviesInTheCategoryById(categoryId).get());
    }

    @Override
    public int createMovie(Movie movie) {
        int result = movieDAO.createMovieTest(movie);
        if (result != 0) {
            movie.setId(result);
            getCatAndAdd(movie);
        }
        return result;
    }

    @Override
    public int updateTimeStamp(int id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.YEAR_MONTH_DAY.getDateFormat()); // made it more type safe :) @bearded redemption
        Timestamp ts = Timestamp.from(Instant.now());
        String date = dateFormat.format(ts);
        return movieDAO.updateTimeStamp(date,id);
    }

    @Override
    public List<Movie> searchMovie(List<Movie> listToSearch, String query, CompareSigns buttonValue, double spinnerValue) {
        return filterUtil.filteringMovies(listToSearch,query,buttonValue,spinnerValue);
    }

    @Override
    public MovieDTO getMovieByNameAPI(String title) {
        return apiService.getMovieByTitle(title);
    }

    @Override
    public List<Movie> getWatchMovies() {
        return movieDAO.getWatchedMovies();
    }

    public int updateMovie(Movie movie) {
        int finalResult = 0; // there is plenty of ways of logic with update categories this one worked for us atm
        Objects.requireNonNull(movie, "Movie cannot be null");

        // tries to update movie
        int resultId = movieDAO.updateMovieById(movie);

        // tries to find and remove the category
        if (resultId > 0) {
            finalResult = getCatNameAndRemove(resultId);
        }
        //  tries to assign the new categories again
        if (finalResult > 0) {
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
        Optional<Movie> fetchedMovie = movieDAO.getMovieById(resultId);
        if (fetchedMovie.isPresent()) {
            resultHere = 1;
            int resultRemove = movieDAO.removeCategoryFromMovie(3);
            if (resultRemove > 0) {
                resultHere = 0;
                AlertHelper.showDefaultAlert("Error: Failed to remove category from movie." + fetchedMovie.get().getId(), Alert.AlertType.ERROR);
            }
        }
        return resultHere;
    }

    /**
     * handles mapping the categories by name and trying to add them to each movie
     * it might be quite slow lets see what up with redis
     *
     * @param movie that will be assigned list of categories
     */
    private void getCatAndAdd(Movie movie) {
        Objects.requireNonNull(movie.getCategories(), "Error: Categories cannot be empty for movie with id: " + movie.getId());

        movie.getCategories().stream()
                .map(category -> categoryService.getCategoryByName(category.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(category -> {
                    int addResult = movieDAO.addCategoryToMovie(category, movie);
                    if (addResult <= 0) {
                        AlertHelper.showDefaultAlert("Error: Failed to add category to movie." + category.getId(), Alert.AlertType.ERROR);
                    }
                });
    }

    @Override
    public boolean playVideoDesktop(int id, String path) throws IOException {
        if (!setPath() || playerPath == null){
            return false;
        }
        if (playerPath.toLowerCase().contains("vlc")) {
            String[] s = new String[]{playerPath, "file:///" + path};
            int result = updateTimeStamp(id);
            if (result <= 0) {
                AlertHelper.showDefaultAlert("Error: Could not update time stamp for movie as last viewed. ", Alert.AlertType.ERROR);
            }
            Runtime.getRuntime().exec(s);
            return true;
        } else {
            AlertHelper.showDefaultAlert("Please use VLC media player", Alert.AlertType.ERROR);
            return false;
        }
    }
    private boolean setPath() {
        try {
            playerPath = Files.readString(Path.of(MEDIA_PLAYER_PATH));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
