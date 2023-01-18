package com.movie_collection.gui.controllers.abstractController;

import javafx.scene.Parent;
import javafx.stage.Stage;

public interface IRootController {
    /**
     * method to retrieve Parent root after it is set in controller factory
     *
     * @return Parent root that is needed in order to construct scene
     */
    Parent getView();

    /**
     * method to set view after it is properly loaded from controller factory
     *
     * @param parent root that will be stored and after retrieved when needed
     */
    void setView(Parent parent);

    /**
     * method to retrieve current stage from the Parent root also all the children that extends the root controller are able to use it
     *
     * @return stage for currently set Parent view
     */
    Stage getStage();
}
