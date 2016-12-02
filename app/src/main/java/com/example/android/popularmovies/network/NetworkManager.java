package com.example.android.popularmovies.network;

import android.util.Log;

import com.example.android.popularmovies.Utils;
import com.example.android.popularmovies.models.MoviesAPIResponse;
import com.example.android.popularmovies.models.Response;
import com.example.android.popularmovies.models.ReviewsApiResponse;
import com.example.android.popularmovies.models.TrailersApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ReeeM on 10/22/2016.
 */
public class NetworkManager {
    private APIInterface cipAPI;

    public NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cipAPI = retrofit.create(APIInterface.class);
    }

    public interface OnCallbackListener {
        public abstract void onSuccess(Response response);

        public abstract void onFailure(Throwable t);
    }

    public void getRequestedMovies(String type, final OnCallbackListener onCallbackListener) {
        Call<MoviesAPIResponse> call = cipAPI.getRequestedMovies(type);
        call.enqueue(new Callback<MoviesAPIResponse>() {
            @Override
            public void onResponse(Call<MoviesAPIResponse> call, retrofit2.Response<MoviesAPIResponse> response) {
                onCallbackListener.onSuccess(response.body());
                Log.d("callback", response.body() + "");
                Log.d("responseCode", response.code() + "");
            }

            @Override
            public void onFailure(Call<MoviesAPIResponse> call, Throwable t) {
                onCallbackListener.onFailure(t);
                Log.e("responseError", t.getLocalizedMessage());
            }
        });
    }

    public void getMovieReviews(String id, final OnCallbackListener onCallbackListener) {
        Call<ReviewsApiResponse> call = cipAPI.getReviews(id);
        call.enqueue(new Callback<ReviewsApiResponse>() {
            @Override
            public void onResponse(Call<ReviewsApiResponse> call, retrofit2.Response<ReviewsApiResponse> response) {
                onCallbackListener.onSuccess(response.body());
                Log.d("callback", response.body() + "");
                Log.d("responseCode", response.code() + "");
            }

            @Override
            public void onFailure(Call<ReviewsApiResponse> call, Throwable t) {
                onCallbackListener.onFailure(t);
                Log.e("responseError", t.getLocalizedMessage());
            }
        });

    }

    public void getMovieTrailers(String id, final OnCallbackListener onCallbackListener) {
        Call<TrailersApiResponse> call = cipAPI.getTrailers(id);
        call.enqueue(new Callback<TrailersApiResponse>() {
            @Override
            public void onResponse(Call<TrailersApiResponse> call, retrofit2.Response<TrailersApiResponse> response) {
                onCallbackListener.onSuccess(response.body());
                Log.d("callback", response.body() + "");
                Log.d("responseCode", response.code() + "");
            }

            @Override
            public void onFailure(Call<TrailersApiResponse> call, Throwable t) {
                onCallbackListener.onFailure(t);
                Log.e("responseError", t.getLocalizedMessage());
            }
        });
    }
}
