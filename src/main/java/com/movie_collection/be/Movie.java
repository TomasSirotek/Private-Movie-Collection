package com.movie_collection.be;

import javafx.beans.property.StringProperty;

import java.sql.Date;

public record Movie(int id, StringProperty name, double rating, StringProperty path, Date lastview) {
}

