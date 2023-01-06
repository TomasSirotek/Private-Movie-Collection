package com.movie_collection.be;

import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.util.List;

public record Movie(int id, StringProperty name, double rating, StringProperty path, List<Category> categories, Date lastview) {
}

