package com.example.android.popularmovies.models;

/**
 * Created by ReeeM on 11/5/2016.
 */
public class TrailersDetails {

    private String id;
    private String key;
    private String name;


    public TrailersDetails(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
