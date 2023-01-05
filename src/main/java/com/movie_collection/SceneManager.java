package com.movie_collection;

import com.google.inject.Inject;
import com.movie_collection.bll.helpers.ViewType;
import com.movie_collection.gui.controllers.IControllerFactory;
import com.movie_collection.gui.controllers.RootController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager implements ISceneManager{
    private Stage stage;

    @Inject
    IControllerFactory controllerFactory;

    @Inject
    public SceneManager(IControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void setRoot(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void loadView(ViewType type,String title) throws IOException {
       Parent parent = loadNodes(type);
       show(parent,title);
    }

    private void show(Parent parent,String title) {
        Scene scene = new Scene(parent,320, 240);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadNodes(ViewType type) throws IOException {
        final RootController rootController = controllerFactory.loadFxmlFile(type);
        return rootController.getView();
    }
}
