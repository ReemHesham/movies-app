package com.example.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.popularmovies.activities.MainActivity;

/**
 * Created by ReeeM on 11/5/2016.
 */
public class Settings {

    private final String MOVIES_TYPE_KEY = "MOVIES_TYPE";

    private SharedPreferences mPreferences;

    public Settings(Context context) {
        mPreferences = context.getSharedPreferences("movies_app_pref", Context.MODE_PRIVATE);
    }

    public void setMoviesType(String value) {
        mPreferences.edit().putString(MOVIES_TYPE_KEY, value).commit();
    }

    public String getMoviesType() {
        return mPreferences.getString(MOVIES_TYPE_KEY, Utils.POPULAR_MOVIES);
    }
}
