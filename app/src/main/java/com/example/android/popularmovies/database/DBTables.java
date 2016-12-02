package com.example.android.popularmovies.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ReeeM on 11/4/2016.
 */
public class DBTables {

    public static final String TABLE_NAME = "movies";
    public static final String MOVIE_ID = "_id";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_POSTER_PATH = "poster_path";
    public static final String MOVIE_OVERVIEW = "overview";
    public static final String MOVIE_RELEASE_DATE = "release_date";
    public static final String MOVIE_VOTE_AVERAGE = "vote_average";

    private static final String CREATE_MOVIE_TABLE =
            " CREATE TABLE " + TABLE_NAME + "(" +
                    MOVIE_ID + " LONG , " +
                    MOVIE_TITLE + " TEXT , " +
                    MOVIE_OVERVIEW + " TEXT , " +
                    MOVIE_RELEASE_DATE + " TEXT , " +
                    MOVIE_VOTE_AVERAGE + " DOUBLE , " +
                    MOVIE_POSTER_PATH + " TEXT" +
                    ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBTables.CREATE_MOVIE_TABLE);
        onCreate(db);
    }
}
