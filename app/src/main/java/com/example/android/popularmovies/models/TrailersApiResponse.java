package com.example.android.popularmovies.models;

import java.util.ArrayList;

/**
 * Created by ReeeM on 11/5/2016.
 */
public class TrailersApiResponse extends Response{

    private ArrayList<TrailersDetails> results;

    public TrailersApiResponse(ArrayList<TrailersDetails> results) {
        this.results = results;
    }

    public ArrayList<TrailersDetails> getResults() {
        return results;
    }

    public void setResults(ArrayList<TrailersDetails> results) {
        this.results = results;
    }
}
