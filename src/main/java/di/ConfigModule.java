package di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.movie_collection.bll.services.CategoryService;
import com.movie_collection.bll.services.MovieService;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.dal.dao.MovieDAO;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;

public class ConfigModule extends AbstractModule {
    @Override
    public void configure(){
        /*
        * Bind the MovieDAO interface to the implementation
         */
        bind(IMovieDAO.class).to(MovieDAO.class);
        /*
         * Injection of binding
         */
        bind(IControllerFactory.class).to(ControllerFactory.class);

        /*
         * Injection of movie service
         */
        bind(IMovieService.class).to(MovieService.class).in(Singleton.class);

        /*
         * Injection of movie service
         */
        bind(ICategoryService.class).to(CategoryService.class).in(Singleton.class);
    }
}
