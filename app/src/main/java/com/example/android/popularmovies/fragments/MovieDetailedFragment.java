package com.example.android.popularmovies.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmovies.PopularMoviesApplication;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils;
import com.example.android.popularmovies.models.MovieData;
import com.example.android.popularmovies.models.Response;
import com.example.android.popularmovies.models.ReviewDetails;
import com.example.android.popularmovies.models.ReviewsApiResponse;
import com.example.android.popularmovies.models.TrailersApiResponse;
import com.example.android.popularmovies.models.TrailersDetails;
import com.example.android.popularmovies.network.NetworkManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ReeeM on 11/5/2016.
 */
public class MovieDetailedFragment extends Fragment {

    private LayoutInflater mLayoutInflater;
    private ViewGroup mContainerViewGroup;

    private OnFavoriteChangedListener onFavoriteChangedListener;

    private NetworkManager mNetworkManger;
    private MovieData mCurrentMovie;
    private LinearLayout mReviewsLayout;
    private LinearLayout mTrailersLayout;
    private ImageView mFavoriteIcon;

    public MovieDetailedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PopularMoviesApplication popularMoviesApplication = (PopularMoviesApplication) getActivity().getApplication();
        mNetworkManger = popularMoviesApplication.getNetworkManager();
        if (getArguments().containsKey(Utils.MOVIE_KEY)) {
            mCurrentMovie = (MovieData) getArguments().getSerializable(Utils.MOVIE_KEY);
            getActivity().setTitle(mCurrentMovie.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detailed, container, false);

        mLayoutInflater = inflater;
        mContainerViewGroup = container;

        mReviewsLayout = (LinearLayout) rootView.findViewById(R.id.reviews_layout);
        mTrailersLayout = (LinearLayout) rootView.findViewById(R.id.trailers_layout);
        mFavoriteIcon = (ImageView) rootView.findViewById(R.id.favorite_icon);

        final ImageView image = (ImageView) rootView.findViewById(R.id.image);
        Picasso.with(getActivity()).load(mCurrentMovie.getPoster_path()).into(image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        ((TextView) rootView.findViewById(R.id.description)).setText(mCurrentMovie.getOverview());
        ((TextView) rootView.findViewById(R.id.user_rating)).setText(mCurrentMovie.getVote_average() + "");
        ((TextView) rootView.findViewById(R.id.release_date)).setText(mCurrentMovie.getRelease_date());

        updateFavorite();

        mFavoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentMovie.isFavoriteMovie(getActivity())) {
                    mCurrentMovie.removeFromFavorite(getContext());
                    mFavoriteIcon.setImageResource(R.drawable.star);
                } else {
                    mCurrentMovie.addToFavoriteList(getContext());
                    mFavoriteIcon.setImageResource(R.drawable.star_active);
                }
                if (onFavoriteChangedListener != null)
                    onFavoriteChangedListener.onFavoriteChanged();
            }
        });


        getMovieReviews(mCurrentMovie.getId() + "");
        getMovieTrailers(mCurrentMovie.getId() + "");

        return rootView;
    }

    public void setOnFavoriteChangedListener(OnFavoriteChangedListener onFavoriteChangedListener) {
        this.onFavoriteChangedListener = onFavoriteChangedListener;
    }

    private void getMovieReviews(String id) {
        mNetworkManger.getMovieReviews(id, new NetworkManager.OnCallbackListener() {
            @Override
            public void onSuccess(Response response) {
                ReviewsApiResponse reviewsApiResponse = (ReviewsApiResponse) response;
                ArrayList<ReviewDetails> reviewDetails = reviewsApiResponse.getResults();
                if (reviewDetails != null && reviewDetails.size() != 0) {
                    for (int i = 0; i < reviewDetails.size(); i++) {
                        ReviewDetails review = reviewDetails.get(i);
                        View reviewView = mLayoutInflater.inflate(R.layout.review_item, mContainerViewGroup, false);
                        ((TextView) reviewView.findViewById(R.id.user_name)).setText(review.getAuthor());
                        ((TextView) reviewView.findViewById(R.id.user_review)).setText(review.getContent());
                        mReviewsLayout.addView(reviewView);
                    }
                } else {
                    mReviewsLayout.findViewById(R.id.no_reviews).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mReviewsLayout.findViewById(R.id.no_reviews).setVisibility(View.VISIBLE);
            }
        });

    }

    private void getMovieTrailers(String id) {

        mNetworkManger.getMovieTrailers(id, new NetworkManager.OnCallbackListener() {
            @Override
            public void onSuccess(Response response) {
                TrailersApiResponse trailersApiResponse = (TrailersApiResponse)response;
                ArrayList<TrailersDetails>trailersDetails = trailersApiResponse.getResults();
                if (trailersDetails != null && trailersDetails.size() != 0) {
                    for (int i = 0; i < trailersDetails.size(); i++) {
                        final TrailersDetails trailer = trailersDetails.get(i);
                        View reviewView = mLayoutInflater.inflate(R.layout.trailer_item, mContainerViewGroup, false);
                        ((TextView) reviewView.findViewById(R.id.trailer_name)).setText(trailer.getName());
                        reviewView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "http://www.youtube.com/watch?v=" + trailer.getKey();
                                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            }
                        });
                        mTrailersLayout.addView(reviewView);
                    }
                } else {
                    mTrailersLayout.findViewById(R.id.no_trailers).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mTrailersLayout.findViewById(R.id.no_trailers).setVisibility(View.VISIBLE);
            }
        });
    }

    public void updateFavorite() {
        if (mCurrentMovie.isFavoriteMovie(getContext()))
            mFavoriteIcon.setImageResource(R.drawable.star_active);
        else
            mFavoriteIcon.setImageResource(R.drawable.star);
    }

    public interface OnFavoriteChangedListener {
        void onFavoriteChanged();
    }
}
