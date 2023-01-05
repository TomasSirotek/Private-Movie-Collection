package com.movie_collection.bll.helpers;

public enum ViewType {
    MAIN {
        @Override
        public String getFXMLView() {
            return "hello-view.fxml";
        }
    },
    MOVIES{
        @Override
        public String getFXMLView() {
            return "movieView.fxml";
        }
    };
    public abstract String getFXMLView();
}
