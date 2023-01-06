package com.movie_collection.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import di.ConfigModule;

public class StartUp {

    private static Injector injector;

    public static void configure(){
        injector = Guice.createInjector(
                new ConfigModule()
        );
    }
    public static Injector getInjector(){
        return injector;
    }

}
