package com.movie_collection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.movie_collection.gui.controllers.IControllerFactory;

public class StartUp {

    private static Injector injector;

    public static void configure(){
        injector = Guice.createInjector(
                new ConfigModule()
        );
      //  injector.getInstance(IControllerFactory.class);
    }
    public static Injector getInjector(){
        return injector;
    }

}
