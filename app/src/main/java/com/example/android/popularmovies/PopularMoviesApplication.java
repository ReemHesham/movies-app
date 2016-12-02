package com.example.android.popularmovies;

import android.app.Application;
import com.example.android.popularmovies.network.NetworkManager;


/**
 * Created by ReeeM on 10/22/2016.
 */

public class PopularMoviesApplication extends Application
{
    public NetworkManager networkManager;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public NetworkManager getNetworkManager()
    {
        if(networkManager == null)
        {
           networkManager = new NetworkManager();
        }
        return networkManager;
    }

}
