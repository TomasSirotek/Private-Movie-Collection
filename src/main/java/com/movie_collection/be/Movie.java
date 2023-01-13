package com.movie_collection.be;

import java.sql.Date;
import java.util.List;


public class Movie {

    private int id;
    private String name;
    private double rating;

    private String absolutePath;

    private Date lastview;

    private List<Category> categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Date getLastview() {
        return lastview;
    }

    public void setLastview(Date lastview) {
        this.lastview = lastview;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Movie2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", absolutePath='" + absolutePath + '\'' +
                ", lastview=" + lastview +
                ", categories=" + categories +
                '}';
    }
}