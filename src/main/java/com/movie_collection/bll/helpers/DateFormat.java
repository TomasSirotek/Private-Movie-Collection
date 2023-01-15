package com.movie_collection.bll.helpers;

public enum DateFormat {
    DAY_MONTH_YEAR{
        @Override
        public String getDateFormat() {
            return "dd/MM/yyyy";
        }
    };

    public abstract String getDateFormat();
}
