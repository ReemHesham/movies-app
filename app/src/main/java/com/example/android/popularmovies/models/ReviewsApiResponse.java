package com.example.android.popularmovies.models;

import java.util.ArrayList;

/**
 * Created by ReeeM on 11/5/2016.
 */
public class ReviewsApiResponse extends Response{

    private ArrayList<ReviewDetails> results;

    public ReviewsApiResponse(ArrayList<ReviewDetails> results) {
        this.results = results;
    }

    public ArrayList<ReviewDetails> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReviewDetails> results) {
        this.results = results;
    }
}
