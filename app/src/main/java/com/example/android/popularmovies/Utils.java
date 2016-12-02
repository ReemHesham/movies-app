package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ReeeM on 10/22/2016.
 */
public class Utils {

    public static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static String API_KEY = "17519abdf490ca55edcb971cf6cdd793";
    public static String MOST_POPULAR = "popular";
    public static String TOP_RATED = "top_rated";
    public static final String POPULAR_MOVIES = "popular";
    public static final String TOP_RATED_MOVIES = "top_rated";
    public static final String FAVORITE_MOVIES = "favorite";
    public static final String MOVIE_KEY = "MOVIE_KEY";

    public static final int MOVIES_LOADER_ID = 0;
    public static final String MOVIES_RESPONSE_KEY = "MOVIES_RESPONSE";
    public static final String MOVIES_TYPE_KEY = "MOVIES_TYPE";

    public static NetworkInfo internetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }



}
