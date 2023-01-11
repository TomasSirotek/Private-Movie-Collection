//package com.movie_collection.dal.mappers;
//
//import com.movie_collection.be.Category2;
//import com.movie_collection.be.Movie2;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.TypeHandler;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//public class CategoryListTypeHandlere implements TypeHandler<List<Category2>> {
//
//
//    @Override
//    public void setParameter(PreparedStatement ps, int i, List<Category2> parameter, JdbcType jdbcType) throws SQLException {
////        if (parameter == null || parameter.isEmpty()) {
////            ps.setString(i, null);
////        } else {
////            //Convert the List<Category2> to a string representation
////            // for example, by concatenating the names of the categories with a separator
////            ps.setString(i, parameter.stream()
////                    .map(Category2::name)
////                    .collect(Collectors.joining(",")));
////        }
//    }
//
//    //Convert String representation back to List
//    @Override
//    public List<Category2> getResult(ResultSet rs, String columnName) throws SQLException {
//        return extractCategories(rs, columnName);
//    }
//
//    @Override
//    public List<Category2> getResult(ResultSet rs, int columnIndex) throws SQLException {
//        return extractCategories(rs, columnIndex);
//    }
//
//    @Override
//    public List<Category2> getResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return extractCategories(cs, columnIndex);
//    }
//
//    private List<Category2> extractCategories(ResultSet rs, int columnIndex) throws SQLException {
//        List<Category2> categories = new ArrayList<>();
//        while (rs.next()) {
//            categories.add(new Category2(rs.getInt(columnIndex + 1), rs.getString(columnIndex + 2)));
//        }
//        return categories;
//    }
//
//
//}
