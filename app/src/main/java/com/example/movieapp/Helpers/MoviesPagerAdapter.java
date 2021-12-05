package com.example.movieapp.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movieapp.Activities.MovieActivity;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.R;

import java.util.List;

public class MoviesPagerAdapter extends PagerAdapter {
    private final Context context;
    private final List<Movie> movieList;
    private final int userID;

    public MoviesPagerAdapter(Context context, List<Movie> movieList,int userID) {
        this.context = context;
        this.movieList = movieList;
        this.userID=userID;
    }

    @Override
    public int getCount() {
        return this.movieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.banner_new_movie_layout,null);
        ImageView bannerImage=view.findViewById(R.id.banner_image);
        Glide.with(context).load(movieList.get(position).getImage()).into(bannerImage);
        container.addView(view);

        bannerImage.setOnClickListener(v -> {
            Intent intent= new Intent(context, MovieActivity.class);
            intent.putExtra("movieID",movieList.get(position).getMovie_id());
            intent.putExtra("name",movieList.get(position).getName());
            intent.putExtra("description",movieList.get(position).getDescription());
            intent.putExtra("image",movieList.get(position).getImage());
            intent.putExtra("link",movieList.get(position).getLink());
            intent.putExtra("releaseYear",movieList.get(position).getReleaseYear());
            intent.putExtra("rating",movieList.get(position).getRating());
            intent.putExtra("userID",userID);
            context.startActivity(intent);
        });
        return view;
    }
}
