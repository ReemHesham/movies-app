package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ReeeM on 11/4/2016.
 */
public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {

    private ArrayList<MovieData> mMovies;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener onFavoriteClickListener;
    private Context mContext;

    public GridViewAdapter(Context context, ArrayList<MovieData> movies) {
        this.mMovies = movies;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnFavoriteClickListener(OnItemClickListener onFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MovieData movie = mMovies.get(position);
        holder.image.setImageBitmap(null);
        Picasso.with(holder.image.getContext()).load(movie.getPoster_path()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, movie);
            }
        });

        if (movie.isFavoriteMovie(mContext))
            holder.favorite.setImageResource(R.drawable.star_active);
        else
            holder.favorite.setImageResource(R.drawable.star);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movie.isFavoriteMovie(mContext)) {
                    movie.removeFromFavorite(mContext);
                    holder.favorite.setImageResource(R.drawable.star);
                } else {
                    movie.addToFavoriteList(mContext);
                    holder.favorite.setImageResource(R.drawable.star_active);
                }
                onFavoriteClickListener.onFavoriteClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null)
            return 0;

        return mMovies.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            favorite = (ImageView) itemView.findViewById(R.id.favorite);
        }
    }

    public void changeList(ArrayList<MovieData> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, MovieData movie);
        void onFavoriteClick();
    }
}
