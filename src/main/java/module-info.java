module com.movie_collection.private_movie_collection {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.movie_collection.private_movie_collection to javafx.fxml;
    exports com.movie_collection.private_movie_collection;
}