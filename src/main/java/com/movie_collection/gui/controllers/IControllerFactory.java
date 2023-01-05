package com.movie_collection.gui.controllers;

import com.movie_collection.bll.helpers.ViewType;

import java.io.IOException;

public interface IControllerFactory {
    RootController loadFxmlFile(ViewType fxmlFile) throws IOException;
}
