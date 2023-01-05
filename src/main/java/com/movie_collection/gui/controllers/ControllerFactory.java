package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.movie_collection.StartUp;
import com.movie_collection.bll.helpers.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ControllerFactory implements IControllerFactory{

    @Inject
    public Injector injector;

    @Inject
    public ControllerFactory(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public RootController loadFxmlFile(ViewType fxmlFile) throws IOException {

        Objects.requireNonNull(fxmlFile, "fxmlFile must not be null.");

        final URL fxmlFileUrl = getClass().getResource(fxmlFile.getFXMLView());

        final FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
        loader.setControllerFactory(injector::getInstance);

        final Parent view = loader.load();
        final RootController controller = loader.getController();
        controller.setView(view);

        return controller;
    }
}
