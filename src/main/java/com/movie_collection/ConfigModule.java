package com.movie_collection;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.movie_collection.gui.controllers.ControllerFactory;
import com.movie_collection.gui.controllers.IControllerFactory;

public class ConfigModule extends AbstractModule {
    @Override
    public void configure(){
        // install(new ServiceModule());
        bind(IControllerFactory.class).to(ControllerFactory.class).in(Singleton.class);
        bind(ISceneManager.class).to(SceneManager.class).in(Singleton.class);
    }
}
