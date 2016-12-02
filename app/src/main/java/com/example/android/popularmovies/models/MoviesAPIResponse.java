package com.example.android.popularmovies.models;

import java.util.ArrayList;

/**
 * Created by ReeeM on 10/22/2016.
 */
public class MoviesAPIResponse extends Response{

    int page;
    ArrayList<MovieData> results;
    int total_results;
    int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MovieData> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieData> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
