package com.movie_collection;

import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.config.StartUp;
import com.movie_collection.gui.controllers.abstractController.IRootController;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StartUp.configure();
        IControllerFactory factory = StartUp.getInjector().getInstance(IControllerFactory.class);
        IRootController controller = factory.loadFxmlFile(ViewType.MAIN);

        Scene scene = new Scene(controller.getView());
        stage.setTitle("Private Movie Collection | The Binary Aces");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}