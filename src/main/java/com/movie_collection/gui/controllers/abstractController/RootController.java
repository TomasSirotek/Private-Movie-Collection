package com.movie_collection.gui.controllers.abstractController;

import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Abstract class that cannot be instantiated it is used for setting up view when its loaded
 * serves just purpose of setting and getting view when it is needed with little help of abstraction
 */
public abstract class RootController implements IRootController {
    protected Parent root;

    @Override
    public Parent getView() {
        return root;
    }

    @Override
    public void setView(Parent node){
        if (this.root != null)
            throw new IllegalStateException("view already set.");

       this.root = Objects.requireNonNull(node, "view must not be null.");
    }

    @Override
    public Stage getStage(){
        return (Stage) root.getScene().getWindow();
    }
}
