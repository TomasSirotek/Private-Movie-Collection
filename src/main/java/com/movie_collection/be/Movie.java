package com.movie_collection.be;

import java.sql.Date;

public record Movie(int id, String name, double rating, String path, Date lastview) {
}

