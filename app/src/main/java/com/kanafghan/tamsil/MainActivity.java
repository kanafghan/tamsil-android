package com.kanafghan.tamsil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.kanafghan.tamsil.adapters.PosterAdapter;
import com.kanafghan.tamsil.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_MOVIES_LIST = "movies";
    public static final String KEY_MOVIE = "movie";

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
                Movie mMovie = mMovieList.get(position);

                Intent movieIntent = new Intent(getApplication(), MovieActivity.class);
                movieIntent.putExtra(KEY_MOVIE, Parcels.wrap(mMovie));
                startActivity(movieIntent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // handle Settings action
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies() {
        FetchMoviesTask task = new FetchMoviesTask();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String sortOrder = settings.getString(
                getString(R.string.pref_sort_order_key),
                getString(R.string.pref_most_popular));

        task.execute(sortOrder);

        String msg = "Loading movies...";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected Movie[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr;

            try {
                // build the URL for requesting the list of movies
                Uri.Builder urlBuilder = new Uri.Builder();
                urlBuilder.scheme("https");
                urlBuilder.authority("api.themoviedb.org");
                urlBuilder.path("/3/discover/movie");
                urlBuilder.appendQueryParameter("sort_by", params[0]);
                urlBuilder.appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY);

                URL url = new URL(urlBuilder.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.v(LOG_TAG, "Did not get any input stream!");
                    return null;
                }

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    Log.v(LOG_TAG, "Stream was empty!");
                    return null;
                }

                moviesJsonStr = buffer.toString();

                try {
                    return this.getMoviesFromJson(moviesJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error getting Movies From JSON: ", e);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieList.clear();
                for (Movie m: movies) {
                    mMovieList.add(m);
                }
                mPosterAdapter.notifyDataSetChanged();
            } else {
                Log.v(LOG_TAG, "Did not receive movies in onPostExecute()!");

                if (!isOnline()) {
                    String msg = "You have no Internet connection!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    String msg = "Failed to fetch any movie due to an unexpected error!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }

        private Movie[] getMoviesFromJson(String moviesJsonStr) throws JSONException {

            final String MDB_MOVIES = "results";
            final String MDB_MOVIE_id = "id";
            final String MDB_MOVIE_POSTER = "poster_path";
            final String MDB_MOVIE_TITLE = "original_title";
            final String MDB_MOVIE_PLOT = "overview";
            final String MDB_MOVIE_RATING = "vote_average";
            final String MDB_MOVIE_RELEASE = "release_date";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(MDB_MOVIES);

            Movie[] result = new Movie[moviesArray.length()];
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieJson = moviesArray.getJSONObject(i);

                Movie mMovie = new Movie(
                        movieJson.getInt(MDB_MOVIE_id),
                        movieJson.getString(MDB_MOVIE_TITLE),
                        movieJson.getString(MDB_MOVIE_POSTER),
                        movieJson.getString(MDB_MOVIE_RELEASE),
                        movieJson.getDouble(MDB_MOVIE_RATING),
                        movieJson.getString(MDB_MOVIE_PLOT)
                );
                result[i] = mMovie;
            }

            return result;
        }

        public boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
    }

}
