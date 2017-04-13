package com.kanafghan.tamsil.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.kanafghan.tamsil.R;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mPosters.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        String posterUrl = BASE_URL + mPosters[position];
        Picasso.with(mContext).load(posterUrl).into(imageView);

        return imageView;
    }

    // references to our images
    private String[] mPosters = {
            "/tWqifoYuwLETmmasnGHO7xBjEtt.jpg", "/45Y1G5FEgttPAwjTYic6czC9xCn.jpg",
            "/s9ye87pvq2IaDvjv9x4IOXVjvA7.jpg", "/5wBbdNb0NdGiZQJYoKHRv6VbiOr.jpg",
            "/myRzRzCxdfUWjkJWgpHHZ1oGkJd.jpg", "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
            "/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg", "/67NXPYvK92oQgEQvLppF2Siol9q.jpg",
            "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "/rXMWOZiCt6eMX22jWuTOSdQ98bY.jpg"
    };

}
