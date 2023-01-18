<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

# Private-Movie-Collection

:school_satchel: Final Exam | 1st Semester | SDE & SCO

## Contributors

<a href="https://github.com/TomassSimko/Private-Movie-Collection/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=TomassSimko/Private-Movie-Collection" />
</a>

---

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://user-images.githubusercontent.com/72190589/207565741-1867a0a5-7bd6-46c8-a985-6e0ac64e4cac.png">
    <img src="https://user-images.githubusercontent.com/72190589/212730015-931a5e47-d9c2-40e7-a77f-0d559aebef49.png" alt="Logo" width="550">
  </a>
  <p align="center">
    Private Movie Collection
    <br />
    <a href="https://github.com/TomassSimko/Private-Movie-Collection"><strong>Explore the docs »</strong></a>
    <br />
  </p>

# The brief

A movie aficionado is buying and collecting movies in the mp4 format on an external 2 TB hard-drive. So far he has tried
to use a system where he puts them in different folders, depending on their rating and the category of the movie. As the
collection has grown the categories is becoming an increasing problem. Here we come with a solution !

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#">About The Project</a>
      <ul>
        <li><a href="#tech-stack">Tech stack</a></li>
        <li><a href="#style">Style</a></li>
      </ul>
    </li>
    <li><a href="#features-and-requirements">Features</a></li>
    <li><a href="#application-design">Application design</a></li>
    <li><a href="#database-design">Database design</a></li>
    <li><a href="#uml-diagram">UML diagram</a></li>
    <li><a href="#application-design-patterns">Application design patterns</a></li>
    <li><a href="#application-interface">Application interface</a></li>
    <li><a href="#licence">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## Tech stack

* [Java](https://www.java.com/en/)
* [Liberica 19](https://bell-sw.com/libericajdk/)
* [JavaFX](https://openjfx.io/)
* [Guava](https://github.com/google/guava)
* [Guice](https://github.com/google/guice)
* [SLF4J](https://www.slf4j.org/)
* [MyBatis](https://mybatis.org/mybatis-3/)
* [Feign](https://github.com/OpenFeign/feign)
* [Logback-classic](https://logback.qos.ch/)
* [SQLServer](https://www.microsoft.com/en-us/sql-server/)

## Style

* [CSS](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference)
* [Bootstrap](https://github.com/dicolar/jbootx)

<!-- ABOUT THE PROJECT -->

## Features and requirements

- [x] MSSQL Database
    - [x] T-SQL dialect
    - [x] MyBatis
    - [x] SL4J logging
    - [x] Build in caching
    - [x] Environmental variables
    - [x] Config file
    - [x] SQL Server Driver
- [x] Movies
    - [x] CRUD
        - [x] Ability to play movie
        - [x] Get all movies
        - [x] Get all movies in category
        - [x] Create movie
        - [x] Update movie
        - [x] Remove movie
    - [x] Updating categories for movie
    - [x] Dynamic update in the interface
    - [x] Custom validation
- [x] Categories
    - [x] CRUD impl
        - [x] Create Category
        - [x] Remove Category
    - [x] Updating categories for movie
    - [x] Dynamic update in the interface
    - [x] Build in drawer with dynamic categories
    - [x] Get all the movies when category assigned
    - [x] Custom validation
- [x] API Contract
    - [x] Open source RESTful web service
    - [x] OpenFeign for smooth API calls
    - [x] Implemented feature to fetch possible movie title from open source api
- [x] Dependency injection
    - [x] Implemented DI with Guice in order to write efficient/reliable code
- [x] Dynamic sorting/filtering
    - [x] Table view with sortable columns
    - [x] Linear search algorithm
        - [x] Search movie by title,year,category
        - [x] Additional search feature for specifying rating
    - [x] Error handling
- [x] Playable video formats
    - [x] Works on Windows
    - [x] Current issues with development environment and permission for mac users
        - [x] Implementation works as intended however only on window atm but the impl works and would work
    - [x] Feature to choose your own preferred movie player
- [x] Initial warning feature requirement
    - [x] User will get the informational option to delete movie that are less than 6 rating and have not been seen for
      2 years at the start

## Application design

Our application was design by us in Figma

![Screenshot 2022-12-14 at 10 23 40](https://user-images.githubusercontent.com/72190589/212730849-76b34eb5-e006-4ce4-ad65-974c64932bfb.png)

## Database design

MSSQL Database diagram

![EASV](https://user-images.githubusercontent.com/72190589/212736702-0c5d451b-d7f7-4166-8b80-0dc58529f3db.png)

## UML diagram

![UML](https://user-images.githubusecontent.com/721989/207556029-147be047-55bd-4140-9b0b-8ac60b41ad57.png)

## Application design patterns

- [x] Singleton pattern
- [x] Dependency injection pattern
- [x] Factory pattern
- [x] Publish-subscribe-style communication
- [x] Data-access-object
- [x] Service pattern

### Data Access Object

Example Method to retrieve movie by its category ID with com.movie_collection.dal.myBatis and SL4J

- Additional mapping included in Movie.xml

``` java
@Override
public Optional<List<Movie>> getAllMoviesInTheCategoryById(int categoryId) {
    List<Movie> fetchedMovieInRole = new ArrayList<>();
    try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
        MovieMapperDAO mapper = session.getMapper(MovieMapperDAO.class);
        fetchedMovieInRole = mapper.getAllMoviesByCategoryId(categoryId);
    } catch (Exception ex) {
        logger.error("An error occurred mapping tables", ex);
    }
    return Optional.ofNullable(fetchedMovieInRole);
}
```

### DI Guice

Dependency injection pattern example of injecting a model via class construct

``` java
private IMovieDAO movieDAO;

@Inject
public MovieService(IMovieDAO movieDAO) {
    this.movieDAO = movieDAO;
}
```

### OpenFeign /w API

RESTful web service to obtain movie information

- http://www.omdbapi.com
- API contract call to get movie by title
- Environmental variables / sensitive credentials handeling
- Custom logger stored in logs

``` java
public interface IMovie {
      @Headers({"Content-Type: application/json","apikey: {apikey}"})
      @RequestLine("GET /?t={title}")
      MovieDTO movieByName(@Param("title") String title);
}
```

### FXML Factory Controller with Guice DI injection

``` java
@Override
public RootController loadFxmlFile(ViewType fxmlFile) throws IOException {
      final URL fxmlFileUrl = Main.class.getResource(fxmlFile.getFXMLView());

      final FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
      loader.setControllerFactory(injector::getInstance);

      final Parent view = loader.load();
      final RootController controller = loader.getController();
      controller.setView(view);

      return controller;
    }
```

### Abstract root controller

- Implementation of a simple abstract class that set and retrieves Parent root object

``` java
public abstract class RootController implements IRootController {
    protected Parent root;

    @Override
    public Parent getView() {
        return root;
    }

    @Override
    public void setView(Parent node){
       this.root = Objects.requireNonNull(node, "view must not be null.");
    }
}
```

## Application interface

![Screenshot 2022-12-14 at 10 41 16]()

## Licence

Distributed under the MIT License. See LICENSE for more information.

Team: isEmpty() {true} <br>
2022 SDE & SCO cs project posted here on GitHub. <br>
Hope you will like it! <br>
Thank you for your attention!
TTT :black_nib:

## Contact

Tomas Simko - [@twitter](https://twitter.com/TomasSimko_) -
simko.t@email.cz - [@linkedIn](https://www.linkedin.com/in/tomas-simko/)  
Patrik Valentíny [@linkedIn](https://www.linkedin.com/in/patrikvalentiny/)

Project Link: [https://github.com/TomassSimko/Private-Movie-Collection]()


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://contrib.rocks/image?repo=TomassSimko/Private-Movie-Collection

[contributors-url]: https://github.com/TomassSimko/Private-Movie-Collection/graphs/contributors

