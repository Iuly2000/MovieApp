package com.example.movieapp.Helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Activities.MovieActivity;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.R;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final List<Movie> movieList;
    private final int userID;

    public MoviesRecyclerAdapter(Context context, List<Movie> movieList,int userID) {
        this.context = context;
        this.movieList = movieList;
        this.userID=userID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movie_short_description_layout, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textRating.setText(Float.toString(this.movieList.get(position).getRating()));
        holder.textYear.setText(Integer.toString(this.movieList.get(position).getReleaseYear()));
        holder.ratingBar.setRating(this.movieList.get(position).getRating());
        Glide.with(context).load(movieList.get(position).getImage()).into(holder.image);
        holder.image.setOnClickListener(v -> {
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
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textRating,textYear;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textRating=itemView.findViewById(R.id.textRating);
            textYear=itemView.findViewById(R.id.textYear);
            ratingBar=itemView.findViewById(R.id.ratingBar);
        }
    }
}
