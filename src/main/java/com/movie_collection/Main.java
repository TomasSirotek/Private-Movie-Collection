package com.movie_collection;

import com.google.inject.Inject;
import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.gui.controllers.IControllerFactory;
import com.movie_collection.gui.controllers.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.inject.Injector;

import java.io.IOException;

public class Main extends Application {
    @Inject
    static
    ISceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
      //  StartUp.configure();
        sceneManager.setRoot(stage);
        sceneManager.loadView(ViewType.MAIN,"Base View");
    }

    public static void main(String[] args) {
        StartUp.configure();
        sceneManager = StartUp.getInjector().getInstance(SceneManager.class);
        launch();
    }
}