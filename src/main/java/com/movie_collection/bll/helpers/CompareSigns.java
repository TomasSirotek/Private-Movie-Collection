package com.movie_collection.bll.helpers;

public enum CompareSigns {

    LESS_THAN_OR_EQUAL("≤"),
    MORE_THAN_OR_EQUAL("≥"),
    EQUAL("=");

    private final String sign;

    CompareSigns(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

}
