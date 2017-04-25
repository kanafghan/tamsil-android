package com.kanafghan.tamsil.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by iceman on 15/04/17.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Movie {

    private final int id;
    private final String title;
    private final String poster;
    private final String releaseDate;
    private final double rating;
    private final String plot;

    @ParcelConstructor
    public Movie(int id, String title, String poster, String releaseDate, double rating, String plot) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.plot = plot;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }
}
