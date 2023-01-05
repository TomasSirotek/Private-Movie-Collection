module com.movie_collection.private_movie_collection {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.guice;


    exports com.movie_collection.gui.controllers;

    opens com.movie_collection.gui.controllers to javafx.fxml;

    opens com.movie_collection to javafx.fxml, com.google.guice;

    exports com.movie_collection.bll.helpers;

    exports com.movie_collection to javafx.graphics;

}