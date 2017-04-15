package com.kanafghan.tamsil.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.kanafghan.tamsil.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PosterAdapter extends ArrayAdapter<Movie> {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    public PosterAdapter(Context c, ArrayList<Movie> mPosters) {
        super(c, 0, mPosters);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(getContext());
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Movie movie = getItem(position);
        if (movie != null) {
            String posterUrl = movie.getPoster();
            Picasso.with(getContext()).load(resolvePosterPath(posterUrl)).into(imageView);
        }

        return imageView;
    }

    private String resolvePosterPath(String relativePath) {
        return BASE_URL + relativePath;
    }
}
