package com.kanafghan.tamsil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.kanafghan.tamsil.adapters.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        mImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(mImageAdapter);

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
        this.fetchMovies();
    }

    private void fetchMovies() {
        for (int i = 0; i < mPosters.length; i++) {
            mImageAdapter.addImage(BASE_URL + mPosters[i]);
        }
    }

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    // references to our images
    private String[] mPosters = {
            "/tWqifoYuwLETmmasnGHO7xBjEtt.jpg", "/45Y1G5FEgttPAwjTYic6czC9xCn.jpg",
            "/s9ye87pvq2IaDvjv9x4IOXVjvA7.jpg", "/5wBbdNb0NdGiZQJYoKHRv6VbiOr.jpg",
            "/myRzRzCxdfUWjkJWgpHHZ1oGkJd.jpg", "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
            "/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg", "/67NXPYvK92oQgEQvLppF2Siol9q.jpg",
            "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "/rXMWOZiCt6eMX22jWuTOSdQ98bY.jpg"
    };
}
