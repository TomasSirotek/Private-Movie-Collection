package com.movie_collection.gui.DTO;

public class MovieDTO {
     public String Title;
     public String Rated;
     public String Released;
     public String Runtime;

     public String Director;

     public String Plot;

     public String Poster;

     public String imdbRating;


    @Override
    public String toString() {
        return "MovieDTO{" +
                "Title='" + Title + '\n' +
                ", Rated='" + Rated + '\n' +
                ", Released='" + Released + '\n' +
                ", Director='" + Director + '\n' +
                ", Runtime='" + Runtime + '\n' +
                ", Plot='" + Plot + '\n' +
                ", Poster='" + Poster + '\n' +
                ", imdbRating='" + imdbRating + '\n' +
                '}';
    }
}
