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

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> mImages;

    public ImageAdapter(Context c) {
        mContext = c;
        mImages = new ArrayList<String>();
    }

    public int getCount() {
        return mImages.size();
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

        String posterUrl = mImages.get(position); //BASE_URL + mPosters[position];
        Picasso.with(mContext).load(posterUrl).into(imageView);

        return imageView;
    }

    public void addImage(String imageUrl) {
        mImages.add(imageUrl);
    }

    public void clearImages() {
        mImages.clear();
    }
}
