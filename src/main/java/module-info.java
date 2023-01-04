module com.movie_collection.private_movie_collection {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.movie_collection.gui.controllers;
    opens com.movie_collection.gui.controllers to javafx.fxml;
    exports com.movie_collection;
    opens com.movie_collection to javafx.fxml;
}