package com.movie_collection.dal.mappers;


import com.movie_collection.be.Movie2;


import java.util.List;
import java.util.Optional;


public interface MovieMapperDAO {

    List<Movie2> getAllMovies();

    List<Movie2> getAllMoviesByCategoryId(int categoryId);



    //    @Select(" SELECT movie.id ,movie.name, movie.rating, movie.path, movie.lastview,category.id as c_category_id, category.name as c_name\n" +
//            "        FROM movie\n" +
//            "                 INNER JOIN CatMovie CM ON Movie.id = CM.movieId\n" +
//            "                 INNER JOIN category ON CM.categoryId = category.id")
////    List<Movie2> getAllMovies();
//
//    final String SELECT_POSTS = "SELECT C.id  as c_category_id,\n" +
//            "               C.name as c_name\n" +
//            "        FROM Movie M\n" +
//            "                 left outer join CatMovie PT on M.id = PT.movieId\n" +
//            "                 left outer join Category C on PT.categoryId = C.id\n" +
//            "        WHERE M.id = #{idMovie}";
////
////    /**
////     * Returns the list of all Blog instances from the database.
////     * @return the list of all Blog instances from the database.
////     */
//    @Select("SELECT id as idMovie ,name,rating,path,lastview from Movie")
//    @Results(value = {
//            @Result(property = "id", column = "idMovie"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "rating", column = "rating"),
//            @Result(property = "path", column = "path"),
//            @Result(property = "lastview", column = "lastview"),
//           @Result(property = "categories", column = "idMovie", javaType = List.class, many = @Many(select = "selectCategories"))
//   })



    /**
     * Returns the list of all Post instances from the database of a Blog
     * @param idMovie
     * @return the list of all Post instances from the database of a Blog
     */
//    @Select(SELECT_POSTS)
//    @Results(value = {
//            @Result(property="id", column="c_category_id"),
//            @Result(property="name", column="c_name"),
//    })
//    List<Category2> selectBlogPosts(int idMovie);

//    /**
//     * Returns the list of all Post instances from the database of a Blog
//     * @param idBlog
//     * @return the list of all Post instances from the database of a Blog
//     */
//    @Select(SELECT_POSTS)
//    @Results(value = {
//            @Result(property="id", column="id"),
//            @Result(property="name", column="name"),
//    })
//    List<Category2> selectCategories(String idBlog);
}


//    @Select("SELECT movie.id as idMovie ,movie.name, movie.rating, movie.path, movie.lastview,categories.id as c_category_id, categories.name as c_name\\n\" +\n" +
//            "//            \"        FROM movie\\n\" +\n" +
//            "//            \"                 INNER JOIN CatMovie CM ON Movie.id = CM.movieId\\n\" +\n" +
//            "//            \"                 INNER JOIN category categories ON CM.categoryId = categories.id")
//    @Results({
//            @Result(id=true, column="tutor_id", property="tutorId"),
//            @Result(column="tutor_name", property="name"),
//            @Result(column="email", property="email"),
//            @Result(property="courses", column="tutor_id",
//                    many=@Many(select="findCategoriesByMovie"))
//    })
//    List<Movie2> getAllMovies();
//}
