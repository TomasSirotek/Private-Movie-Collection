package com.movie_collection;

import com.movie_collection.bll.helpers.ViewType;
import javafx.stage.Stage;

import java.io.IOException;

public interface ISceneManager {
    void setRoot(Stage stage);
    void loadView(ViewType type,String title)  throws IOException;
}
