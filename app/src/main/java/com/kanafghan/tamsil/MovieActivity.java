package com.kanafghan.tamsil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.kanafghan.tamsil.models.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import static com.kanafghan.tamsil.adapters.PosterAdapter.resolvePosterPath;

public class MovieActivity extends AppCompatActivity {

    protected Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setupActionBar();
        setTitle(R.string.title_activity_movie_details);

        mMovie = Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.KEY_MOVIE));
    }

    @Override
    protected void onStart() {
        if (mMovie != null) {
            TextView titleView = (TextView) findViewById(R.id.movie_title);
            if (titleView != null) {
                titleView.setText(mMovie.getTitle());
            }

            ImageView posterView = (ImageView) findViewById(R.id.movie_poster);
            if (posterView != null) {
                posterView.setAdjustViewBounds(true);
                String posterUrl = resolvePosterPath(mMovie.getPoster());
                Picasso.with(this)
                        .load(posterUrl)
                        .placeholder(R.drawable.movie_poster_placeholder)
                        .error(R.drawable.movie_poster_placeholder)
                        .into(posterView);
            }

            TextView releaseDateView = (TextView) findViewById(R.id.movie_release_date);
            if (releaseDateView != null) {
                releaseDateView.setText(mMovie.getReleaseDate());
            }

            TextView ratingView = (TextView) findViewById(R.id.movie_rating);
            if (ratingView != null) {
                ratingView.setText(mMovie.getRating() + "/10");
            }

            TextView plotView = (TextView) findViewById(R.id.movie_plot);
            if (plotView != null) {
                plotView.setText(mMovie.getPlot());
            }
        }

        super.onStart();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            return true;
        }

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
