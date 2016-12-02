package com.example.android.popularmovies.network;

import com.example.android.popularmovies.Utils;
import com.example.android.popularmovies.models.MoviesAPIResponse;
import com.example.android.popularmovies.models.ReviewsApiResponse;
import com.example.android.popularmovies.models.TrailersApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ReeeM on 10/22/2016.
 */
public interface APIInterface {
    public static String API_KEY = "17519abdf490ca55edcb971cf6cdd793";
    public static String REQUESTED_MOVIES = "{type}?api_key=" + API_KEY;
    public static String REQUESTED_REVIEWS = "{id}/reviews?api_key=" + API_KEY;
    public static String REQUESTED_TRAILERS = "{id}/videos?api_key=" + API_KEY;

    @GET(REQUESTED_MOVIES)
    Call<MoviesAPIResponse> getRequestedMovies(@Path("type") String type);
    @GET(REQUESTED_REVIEWS)
    Call<ReviewsApiResponse> getReviews(@Path("id") String id);

    @GET(REQUESTED_TRAILERS)
    Call<TrailersApiResponse> getTrailers(@Path("id") String id);
}