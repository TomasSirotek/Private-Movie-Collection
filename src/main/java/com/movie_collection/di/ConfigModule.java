package com.movie_collection.di;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.movie_collection.bll.services.CategoryService;
import com.movie_collection.bll.services.MovieService;
import com.movie_collection.bll.services.api.APIService;
import com.movie_collection.bll.services.interfaces.IAPIService;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.bll.util.Filter;
import com.movie_collection.bll.util.IFilter;
import com.movie_collection.dal.dao.CategoryDAO;
import com.movie_collection.dal.dao.MovieDAO;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import com.movie_collection.gui.models.CategoryModel;
import com.movie_collection.gui.models.ICategoryModel;
import com.movie_collection.gui.models.IMovieModel;
import com.movie_collection.gui.models.MovieModel;

public class ConfigModule extends AbstractModule {
    @Override
    public void configure() {

        /* *************************************************************************
         *                                                                         *
         * CONTROLLER                                                                  *
         *                                                                         *
         **************************************************************************

        /*
         * Injection of binding
         */
        bind(IControllerFactory.class).to(ControllerFactory.class);


        /* *************************************************************************
         *                                                                         *
         * SERVICE                                                                 *
         *                                                                         *
         **************************************************************************

        /*
         * Injection of movie service
         */
        bind(IMovieService.class).to(MovieService.class);

        /*
         * Injection of movie service
         */
        bind(ICategoryService.class).to(CategoryService.class);

        /*
         * Binds api service
         */

        bind(IAPIService.class).to(APIService.class);

        /* *************************************************************************
        *                                                                         *
        * MODEL                                                                   *
        *                                                                         *
        **************************************************************************

        /*
         * Injection of movie service
         * Singleton due to the usability and to have just one instance
         */
        bind(ICategoryModel.class).to(CategoryModel.class).in(Singleton.class);

        /*
         * Injection of movie model
         */
        bind(IMovieModel.class).to(MovieModel.class).in(Singleton.class);

        /* *************************************************************************
        *                                                                         *
        * DAO                                                                     *
        *                                                                         *
        **************************************************************************
        /*
         * Bind the MovieDAO interface to the implementation
         */
        bind(IMovieDAO.class).to(MovieDAO.class);
        /*
         * Bind the CategoryDAO interface to the implementation
         */
        bind(ICategoryDAO.class).to(CategoryDAO.class);

        /* *************************************************************************
        *                                                                         *
        * EVENT                                                                   *
        *                                                                         *
        **************************************************************************

        /*
         * Bind even bus as in singleton scope
         * As eager singleton to ensure instantiation asap Injector is created
         */
        bind(EventBus.class).asEagerSingleton();

        /* *************************************************************************
        *                                                                         *
        * HELPER                                                                   *
        *                                                                         *
        **************************************************************************

        /*
         * Injection of Filter helper
         */
        bind(IFilter.class).to(Filter.class);
    }
}
