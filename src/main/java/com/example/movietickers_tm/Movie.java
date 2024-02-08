package com.example.movietickers_tm;

import java.sql.Date;
import java.sql.Time;

public class Movie
{
    private String movieName, movieGenre, movieDuration;
    private Date moviePublishDate;


    public Movie(String name, String genre, Date date, String duration)
    {
        movieName = name;
        movieGenre = genre;
        moviePublishDate = date;
        movieDuration = duration;
    }

    //region accessors

    public String getMovieDuration()
    {
        return movieDuration;
    }

    public String getMovieGenre()
    {
        return movieGenre;
    }

    public String getMovieName()
    {
        return movieName;
    }

    public Date getMoviePublishDate()
    {
        return moviePublishDate;
    }
    //endregion

    //region mutators

    public void setMovieDuration(String movieDuration)
    {
        this.movieDuration = movieDuration;
    }

    public void setMovieGenre(String movieGenre)
    {
        this.movieGenre = movieGenre;
    }

    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }

    public void setMoviePublishDate(Date moviePublishDate)
    {
        this.moviePublishDate = moviePublishDate;
    }

    //endregion

}
