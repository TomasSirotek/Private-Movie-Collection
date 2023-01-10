package com.movie_collection.bll.helpers;

/**
 * Enum with abstract method that forces each individual ENUM to override returning string with its own
 * so if for example MAIN is used it ensures that it will only return views/base.fxml
 */
public enum ViewType {
    MAIN {
        @Override
        public String getFXMLView() {
            return "views/base.fxml";
        }
    },
    MOVIES{
        @Override
        public String getFXMLView() {
            return "views/movies-view.fxml";
        }
    },
    CREATE_EDIT {
        @Override
        public String getFXMLView() {
            return "views/movie-create-edit-view.fxml";
        }
    }, CATEGORY_ADD_EDIT {
        @Override
        public String getFXMLView() {
            return "views/category-add-edit-view.fxml";
        }
    };
    public abstract String getFXMLView();
}
