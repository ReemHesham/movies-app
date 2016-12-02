package com.example.android.popularmovies.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.android.popularmovies.database.DBTables;
import com.example.android.popularmovies.database.MoviesProvider;

import java.io.Serializable;

/**
 * Created by ReeeM on 10/22/2016.
 */
public class MovieData implements Serializable{
    String poster_path;
    boolean adult;
    String overview;
    String release_date;
    int[] genre_ids;
    long id;
    String original_title;
    String original_language;
    String title;
    String backdrop_path;
    double popularity;
    int vote_count;
    boolean video;
    double vote_average;
    private boolean mIsFavoriteMovie;
    private final String image_url = "http://image.tmdb.org/t/p/w185/";


    public MovieData(String poster_path, String overview, String release_date, long id, String title, double vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return(image_url + poster_path);
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isFavoriteMovie(Context context) {
        String whereClause = DBTables.MOVIE_ID + "=" + getId();
        Cursor cursor = context.getContentResolver().query(
                MoviesProvider.MOVIES_URI, null, whereClause, null, null);
        setIsFavorite(cursor.moveToFirst());
        cursor.close();

        return mIsFavoriteMovie;
    }

    public void setIsFavorite(boolean favorite) {
        mIsFavoriteMovie = favorite;
    }

    public void addToFavoriteList(Context context) {

        setIsFavorite(true);

        ContentValues values = new ContentValues();
        values.put(DBTables.MOVIE_ID, getId());
        values.put(DBTables.MOVIE_TITLE, getTitle());
        values.put(DBTables.MOVIE_OVERVIEW, getOverview());
        values.put(DBTables.MOVIE_POSTER_PATH, poster_path);
        values.put(DBTables.MOVIE_VOTE_AVERAGE, getVote_average());
        values.put(DBTables.MOVIE_RELEASE_DATE, getRelease_date());

        context.getContentResolver().insert(MoviesProvider.MOVIES_URI, values);
    }

    public void removeFromFavorite(Context context) {

        setIsFavorite(false);

        String whereClause = DBTables.MOVIE_ID + "=" + getId();
        context.getContentResolver().delete(MoviesProvider.MOVIES_URI, whereClause, null);
    }
}
