package com.movie_collection.bll.helpers;

public enum DateFormat {
    YEAR_MONTH_DAY {
        @Override
        public String getDateFormat() {
            return "YYYY-MM-DD";
        }
    };

    public abstract String getDateFormat();
}
