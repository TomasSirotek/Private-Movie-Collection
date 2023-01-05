package com.movie_collection.be;

import java.util.Calendar;

public record Movie(int id, String name, double rating, String path, Calendar lastview) {
}

