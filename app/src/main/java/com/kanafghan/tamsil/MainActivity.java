package com.kanafghan.tamsil;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.kanafghan.tamsil.adapters.PosterAdapter;
import com.kanafghan.tamsil.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_MOVIES_LIST = "movies";
    private PosterAdapter mPosterAdapter;
    private ArrayList<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_MOVIES_LIST)) {
            mMovieList = Parcels.unwrap(savedInstanceState.getParcelable(KEY_MOVIES_LIST));
        } else {
            mMovieList = new ArrayList<Movie>();
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);

        mPosterAdapter = new PosterAdapter(this, mMovieList);
        gridview.setAdapter(mPosterAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mMovieList.isEmpty()) {
            this.fetchMovies();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_MOVIES_LIST, Parcels.wrap(mMovieList));
        super.onSaveInstanceState(outState);
    }

    private void fetchMovies() {
        for (int i = 0; i < mPosters.length; i++) {
            mMovieList.add(new Movie(mPosters[i]));
        }
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
