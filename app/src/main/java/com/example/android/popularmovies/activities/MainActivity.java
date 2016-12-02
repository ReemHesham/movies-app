package com.example.android.popularmovies.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Settings;
import com.example.android.popularmovies.Utils;
import com.example.android.popularmovies.PopularMoviesApplication;
import com.example.android.popularmovies.adapters.GridViewAdapter;
import com.example.android.popularmovies.database.DBTables;
import com.example.android.popularmovies.database.MoviesProvider;
import com.example.android.popularmovies.fragments.MovieDetailedFragment;
import com.example.android.popularmovies.models.MovieData;
import com.example.android.popularmovies.models.MoviesAPIResponse;
import com.example.android.popularmovies.models.Response;
import com.example.android.popularmovies.network.NetworkManager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements GridViewAdapter.OnItemClickListener, MovieDetailedFragment.OnFavoriteChangedListener {


    private NetworkManager mNetworkManger;
    private RecyclerView mRecyclerView;
    private GridViewAdapter mGridViewAdapter;
    private String mMoviesType = "";
    private Settings mSettings;
    private ArrayList<MovieData> mMoviesList;
    private boolean mIsTabletView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        setupRecyclerView();
        PopularMoviesApplication popularMoviesApplication = (PopularMoviesApplication) getApplication();
        mNetworkManger = popularMoviesApplication.getNetworkManager();
        mSettings = new Settings(this);
        setupRecyclerView();

        if (savedInstanceState == null) {
            loadData();
        } else {
            mMoviesList = (ArrayList<MovieData>) savedInstanceState.getSerializable(Utils.MOVIES_RESPONSE_KEY);
            mMoviesType = savedInstanceState.getString(Utils.MOVIES_TYPE_KEY);
            if (mMoviesList == null)
                loadData();
            else {
                mGridViewAdapter.changeList(mMoviesList);
            }

            setTitle();
        }
        if (findViewById(R.id.item_detail_container) != null)
            mIsTabletView = true;
        getLoaderManager().initLoader(Utils.MOVIES_LOADER_ID, null, moviesLoader);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGridViewAdapter != null)
            mGridViewAdapter.notifyDataSetChanged();

        MovieDetailedFragment movieDetailsFragment = (MovieDetailedFragment) getSupportFragmentManager().findFragmentById(R.id.item_detail_container);
        if (movieDetailsFragment != null)
            movieDetailsFragment.setOnFavoriteChangedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (mMoviesList != null) {
            outState.putSerializable(Utils.MOVIES_RESPONSE_KEY, mMoviesList);
            outState.putString(Utils.MOVIES_TYPE_KEY, mMoviesType);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mostPopular) {
            mSettings.setMoviesType(Utils.MOST_POPULAR);
            loadData();
            return true;
        }

        if (id == R.id.action_topRated) {
            mSettings.setMoviesType(Utils.TOP_RATED);
            loadData();
            return true;
        }
        if (id == R.id.action_favourite) {
            mSettings.setMoviesType(Utils.FAVORITE_MOVIES);
            loadData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRequestedMovies(String type) {

        mNetworkManger.getRequestedMovies(type, new NetworkManager.OnCallbackListener() {
            @Override
            public void onSuccess(Response response) {
                MoviesAPIResponse moviesAPIResponse = (MoviesAPIResponse) response;
                mMoviesList = moviesAPIResponse.getResults();
                mGridViewAdapter.changeList(mMoviesList);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_gridView);
        assert mRecyclerView != null;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getResources().getBoolean(R.bool.isTablet))
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            else
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            if (getResources().getBoolean(R.bool.isTablet))
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            else
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        mGridViewAdapter = new GridViewAdapter(this, null);
        mGridViewAdapter.setOnItemClickListener(this);
        mGridViewAdapter.setOnFavoriteClickListener(this);
        mRecyclerView.setAdapter(mGridViewAdapter);
    }

    private void loadData() {
        setTitle();
        mMoviesType = mSettings.getMoviesType();
        if (mMoviesType.equalsIgnoreCase(Utils.FAVORITE_MOVIES)) {
            getLoaderManager().restartLoader(Utils.MOVIES_LOADER_ID, null, moviesLoader);
        } else {

            getRequestedMovies(mMoviesType);
        }
    }

    @Override
    public void onItemClick(View view, MovieData movie) {

        if (mIsTabletView) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(Utils.MOVIE_KEY, movie);
            MovieDetailedFragment movieDetailsFragment = new MovieDetailedFragment();
            movieDetailsFragment.setOnFavoriteChangedListener(this);
            movieDetailsFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, movieDetailsFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailedActivity.class);
            intent.putExtra(Utils.MOVIE_KEY, movie);
            startActivity(intent);
        }
    }

    @Override
    public void onFavoriteClick() {
        MovieDetailedFragment movieDetailedFragment = (MovieDetailedFragment) getSupportFragmentManager().findFragmentById(R.id.item_detail_container);
        if (movieDetailedFragment != null)
            movieDetailedFragment.updateFavorite();
    }

    private void setTitle() {
        String type = mSettings.getMoviesType();
        if (type.equalsIgnoreCase(Utils.POPULAR_MOVIES)) {
            setTitle(R.string.popular_title);
        } else if (type.equalsIgnoreCase(Utils.TOP_RATED_MOVIES)) {
            setTitle(R.string.top_title);
        } else {
            setTitle(R.string.favorite_title);
        }
    }


    private LoaderManager.LoaderCallbacks<Cursor> moviesLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(MainActivity.this, MoviesProvider.MOVIES_URI, null, null, null, DBTables.MOVIE_ID + " ASC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

            if (!mMoviesType.equalsIgnoreCase(Utils.FAVORITE_MOVIES))
                return;

            ArrayList<MovieData> movies = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    MovieData movie = new MovieData(cursor.getString(cursor.getColumnIndex(DBTables.MOVIE_POSTER_PATH)),
                            cursor.getString(cursor.getColumnIndex(DBTables.MOVIE_OVERVIEW)),
                            cursor.getString(cursor.getColumnIndex(DBTables.MOVIE_RELEASE_DATE)),
                            cursor.getLong(cursor.getColumnIndex(DBTables.MOVIE_ID)),
                            cursor.getString(cursor.getColumnIndex(DBTables.MOVIE_TITLE)),
                            cursor.getDouble(cursor.getColumnIndex(DBTables.MOVIE_VOTE_AVERAGE)));
                    movies.add(movie);
                    Log.w("posterPath", cursor.getString(cursor.getColumnIndex(DBTables.MOVIE_POSTER_PATH)));
                } while ((cursor.moveToNext()));
            } else {
            }
            mGridViewAdapter.changeList(movies);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }
    };

    @Override
    public void onFavoriteChanged() {
        if (mGridViewAdapter != null)
            mGridViewAdapter.notifyDataSetChanged();
    }
}
