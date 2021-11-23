package com.example.movieapp.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.R;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MyViewHolder> {
    Context context;
    List<Movie> movieList;

    public MoviesRecyclerAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movie_and_description_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.movieDescription.setText(this.movieList.get(position).getDescription());
        Glide.with(context).load(movieList.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView movieDescription;
        Button playButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            movieDescription = itemView.findViewById(R.id.movie_description);
            playButton = itemView.findViewById(R.id.playButton);
        }
    }
}
