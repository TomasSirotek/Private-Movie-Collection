package com.movie_collection.dal.mappers;


import com.movie_collection.be.Movie2;
import org.apache.ibatis.annotations.Param;


import java.sql.Date;
import java.util.List;
import java.util.Optional;


public interface MovieMapperDAO {

    List<Movie2> getAllMovies();

    Movie2 getMovieById(@Param("userValue") int id);

    List<Movie2> getAllMoviesByCategoryId(@Param("value") int categoryId);

    int createMovieTest(Movie2 movie2);
    int updateMovie(@Param("name") String name,@Param("rating") double rating,@Param("path") String path,@Param("id") int id);

    int deleteMovieById(@Param("value") int id);

    int addCategoryToMovie(@Param("catValue") int categoryId, @Param("movieValue") int movieId);

    int removeCategoryMovie(@Param("movieId") int id);


}
