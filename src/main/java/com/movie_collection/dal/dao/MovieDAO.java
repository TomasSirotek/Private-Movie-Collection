package com.movie_collection.dal.dao;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;
import com.movie_collection.dal.ConnectionManager;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.dal.mappers.MovieMapperDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO implements IMovieDAO {
   private static final ConnectionManager cm = new ConnectionManager();

    public static void main(String[] args) {
       // var test = getAllMoviesTest();
       //test.forEach(System.out::println);
//        System.out.println(test.size());
//       // var getAllMoviesInTheCategoryTest = getAllMoviesInTheCategoryTest(1210);
//
////        getAllMoviesInTheCategoryTest(5).ifPresent(allMoviesInCategory -> {
////            for (Movie2 movie : allMoviesInCategory) {
////                System.out.println(movie);
////            }
////        });
//
//        int toDelete = 1134;
//        int resultDelete = deleteMovieTest(toDelete);
//        if(resultDelete > 0){
//            System.out.println("Deleted with id: " + toDelete);
//        }else {
//            System.out.println("Could not delete with id " + toDelete);
//        }

//        var test2 = getAllMoviesTest();
//        System.out.println(test2.size());
//
        Movie2 movieToCreate = new Movie2();


        movieToCreate.setName("TestMovieBatis2222");
        movieToCreate.setRating(4.9);
        movieToCreate.setAbsolutePath("/C:Tomas/hereeee");

        //Movie2 result = createMovieTest(movieToCreate);
//        if(result != null){
//        System.out.println(result.toString() + "We did it Jesus");
//
//        }
//
//        var afterCreate = getAllMoviesTest();
//        System.out.println(afterCreate.size());


//        Movie2 movieToCreateId = new Movie2();
//        movieToCreateId.setId(1205);
//
//        Category2 category2 = new Category2();
//        category2.setId(26);
//
//
//        int categoryResult = addCategoryToMovie(category2,movieToCreateId);
//        System.out.println(categoryResult);
    }


    @Override
    public List<Movie2> getAllMoviesTest(){
        List<Movie2> allMovies;
        long starttime;

        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            starttime = System.currentTimeMillis();
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            allMovies = mapper.getAllMovies();
        }
        System.out.println("Time to get all the movies: " + (System.currentTimeMillis() - starttime));
        return allMovies;
    }

    public static Optional<List<Movie2>> getAllMoviesInTheCategoryTest(int categoryId)  {
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            return Optional.ofNullable(mapper.getAllMoviesByCategoryId(categoryId));
        }
    }

    @Override
    public int createMovieTest(Movie2 movie){
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.createMovieTest(movie);
            session.commit();//  after commit if rows > 0 returns the generated key
            return affectedRows > 0 ? movie.getId() : 0;
        }
    }
    @Override
    public int addCategoryToMovie(Category2 category2,Movie2 movie2){
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.addCategoryToMovie(category2.getId(),movie2.getId());
            session.commit();
            return affectedRows;
        }
    }

    public static int deleteMovieTest(int id){
        try(SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
            int affectedRows = mapper.deleteMovieById(id);
            session.commit(); // end a unit of work -> if u want to do more open new session -> ensure no leftovers
            return affectedRows;
        }
    }

    // -> only use when inserting write update delete ->  session.commit(); // end a unit of work -> if u want to do more open new session




//    public List<Movie> getAllMovies() throws SQLException {
//        List<Movie> movies = new ArrayList<>();
//        long starttime;
//        try (Connection con = cm.getConnection()){
//            starttime = System.currentTimeMillis();
//            String sql = "SELECT M.id, M.name, M.rating, M.path, M.lastview, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Movie M " +
//                    "LEFT JOIN CatMovie CM on M.id = CM.movieId " +
//                    "LEFT JOIN Category C on C.id = CM.categoryId " +
//                    "ORDER BY M.id ASC;";
//            PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = pstmt.executeQuery();
//
//            // used for adding categories to movie
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                StringProperty name = new SimpleStringProperty(rs.getString("name"));
//                double rating = rs.getDouble("rating");
//                StringProperty path = new SimpleStringProperty(rs.getString("path"));
//                Date lastview = rs.getDate("lastview");
//
//                Movie lastMovie;
//                List<Category> categories;
//                // check for empty list
//                if (movies.isEmpty()) {
//                    // add first element
//                    String catName = rs.getString("C_name");
//                    categories = catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>();
//                    movies.add(new Movie(id, name, rating, path, categories, lastview));
//                } else {
//                    // the last added movie
//                    lastMovie = movies.get(movies.size() - 1);
//                    // if it is the same as movie in this row
//                    if (lastMovie.id() == id) {
//                        // add the extra category
//                        categories = new ArrayList<>(lastMovie.categories());
//                        categories.add(new Category(rs.getInt("C_id"), new SimpleStringProperty(rs.getString("C_name"))));
//                        // remove the old movie
//                        movies.remove(lastMovie);
//                    } else {
//                        String catName = rs.getString("C_name");
//                        categories = catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>();
//                    }
//                    movies.add(new Movie(id, name, rating, path, new ArrayList<>(categories), lastview));
//
//                }
//            }
//        }
//        System.out.println("Time to get all the movies: " + (System.currentTimeMillis() - starttime));
//        return (movies);
//    }
//
//    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
//        ArrayList<Movie> movies = new ArrayList<>();
//        try (Connection con= cm.getConnection()) {
//            String sql = "SELECT M.id, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Category C  " +
//                    "LEFT JOIN CatMovie CM on C.id = CM.categoryId " +
//                    "LEFT JOIN Movie M on M.id = CM.movieId WHERE C.id = ? " +
//                    "ORDER BY M.id ASC;";
//            PreparedStatement preparedStatement = con.prepareStatement(sql);
//            preparedStatement.setInt(1, categoryId);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                movies.add(getMovieById(id));
//            }
//        }
//        return movies;
//    }
//
//    public Movie getMovieById(int id) throws SQLException {
//        try (Connection con = cm.getConnection()) {
//            String sql = "SELECT M.id, M.name, M.rating, M.path, M.lastview, CM.categoryId, CM.movieId, C.id as C_id, C.name as C_name FROM Movie M  " +
//                    "LEFT JOIN CatMovie CM on M.id = CM.movieId " +
//                    "LEFT JOIN Category C on C.id = CM.categoryId WHERE M.id = ? " +
//                    "ORDER BY M.id ASC, C.id ASC;";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            rs.next();
//
//            StringProperty name = new SimpleStringProperty(rs.getString("name"));
//            double rating = rs.getDouble("rating");
//            StringProperty path = new SimpleStringProperty(rs.getString("path"));
//            Date lastview = rs.getDate("lastview");
//            String catName = rs.getString("C_name");
//
//            ArrayList<Category> allCategories = new ArrayList<>(catName != null ? List.of(new Category(rs.getInt("C_id"), new SimpleStringProperty(catName))) : new ArrayList<>());
//            while (rs.next()) {
//                allCategories.add(new Category(rs.getInt("C_id"), new SimpleStringProperty(rs.getString("C_name"))));
//            }
//
//            return new Movie(id, name, rating, path, allCategories, lastview);
//        }
//    }

    @Override
    public List<Movie> getAllMovies() throws SQLException {
        return null;
    }

//    @Override
//    public List<Movie> getAllMoviesInTheCategory(int categoryId) throws SQLException {
//        return null;
//    }

    @Override
    public Movie getMovieById(int id) throws SQLException {
        return null;
    }

//    public static int createMovie(Movie movie) throws SQLException {
//        int rowsAffected = 0;
//        try (Connection con = cm.getConnection()) {
//            String sql;
//            PreparedStatement pstmt;
//            sql = "INSERT INTO Movie (name, rating, path, lastview) VALUES (?, ?, ?, ?); SELECT SCOPE_IDENTITY() as id";
//            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setString(1, movie.name().get());
//            pstmt.setDouble(2, movie.rating());
//            pstmt.setString(3, movie.absolutePath().get());
//            pstmt.setDate(4, movie.lastview());
//            ResultSet rs = pstmt.executeQuery();
//            rs.next();
//            int id = rs.getInt("id");
//
//            rowsAffected = linkMovieCategories(movie, rowsAffected, con, id);
//        }
//        return rowsAffected + 1;
//    }

    public int updateMovie(Movie movie) throws SQLException {
        int rowsAffected = 1;
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Movie SET name = ?, rating = ?, path = ?, lastview = ? OUTPUT INSERTED.id WHERE name = (?) ";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name().get());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.absolutePath().get());
            pstmt.setDate(4, movie.lastview());
            pstmt.setString(5, movie.name().get());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");

            sql = "DELETE FROM CatMovie WHERE movieId = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            rowsAffected = linkMovieCategories(movie, rowsAffected, con, id);
        }
        return rowsAffected;
    }

    private int linkMovieCategories(Movie movie, int rowsAffected, Connection con, int id) throws SQLException {
        String sql;
        PreparedStatement pstmt;
        sql = "INSERT INTO CatMovie (categoryId, movieId) VALUES (?, ?);";
        pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (Category c : movie.categories()) {
            pstmt.setInt(1, c.id());
            pstmt.setInt(2, id);
            pstmt.addBatch();
            rowsAffected++;
        }
        pstmt.executeBatch();
        return rowsAffected;
    }

    public int deleteMovie(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql2 = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, id);
            return pstmt2.executeUpdate();
        }
    }
}
