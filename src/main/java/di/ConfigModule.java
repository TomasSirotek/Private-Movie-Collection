package di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.movie_collection.bll.services.CategoryService;
import com.movie_collection.bll.services.MovieService;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.dal.dao.CategoryDAO;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.gui.controllers.BaseController;
import com.movie_collection.dal.dao.MovieDAO;
import com.movie_collection.dal.interfaces.IMovieDAO;
import com.movie_collection.gui.controllers.controllerFactory.IControllerFactory;
import com.movie_collection.gui.controllers.controllerFactory.ControllerFactory;
import com.movie_collection.gui.models.CategoryModel;
import com.movie_collection.gui.models.ICategoryModel;
import com.movie_collection.gui.models.IMovieModel;
import com.movie_collection.gui.models.MovieModel;

public class ConfigModule extends AbstractModule {
    @Override
    public void configure(){
        /*
        * Bind the MovieDAO interface to the implementation
         */
        bind(IMovieDAO.class).to(MovieDAO.class);
        /*
         * Bind the CategoryDAO interface to the implementation
         */
        bind(ICategoryDAO.class).to(CategoryDAO.class);
        /*
         * Injection of binding
         */
        bind(IControllerFactory.class).to(ControllerFactory.class);

        /*
         * Injection of movie service
         */
        bind(IMovieService.class).to(MovieService.class).in(Singleton.class);

        /*
         * Injection of Category service
         */
        bind(ICategoryService.class).to(CategoryService.class).in(Singleton.class);
        
        /*
         * Injection of Category service
         */
        bind(ICategoryModel.class).to(CategoryModel.class).in(Singleton.class);


        /*
         * Injection of movie service
         */
        bind(IMovieModel.class).to(MovieModel.class).in(Singleton.class);
        /*
         * Injection of base controller
         */
        bind(BaseController.class).in(Singleton.class);

    }
}
