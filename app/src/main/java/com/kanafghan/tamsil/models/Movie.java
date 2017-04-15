package com.kanafghan.tamsil.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by iceman on 15/04/17.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Movie {

    private String poster;

    @ParcelConstructor
    public Movie(String poster) {
        this.poster = poster;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
