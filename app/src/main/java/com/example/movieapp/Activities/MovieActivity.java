package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.R;

public class MovieActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView movieDescription, ratingText, releaseText;
    private ImageView image;
    private Button playButton;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ratingBar = findViewById(R.id.ratingBar);
        movieDescription = findViewById(R.id.movie_description);
        ratingText = findViewById(R.id.ratingText);
        releaseText = findViewById(R.id.releaseText);
        image = findViewById(R.id.image);
        playButton = findViewById(R.id.playButton);

        currentMovie = new Movie(
                getIntent().getIntExtra("movieID", 0),
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("description"),
                getIntent().getStringExtra("image"),
                getIntent().getStringExtra("link"),
                getIntent().getIntExtra("releaseYear", 0),
                getIntent().getFloatExtra("rating", 0)
        );

        ratingBar.setRating(currentMovie.getRating());
        movieDescription.setText(currentMovie.getDescription());
        ratingText.setText(Float.toString(currentMovie.getRating()));
        releaseText.setText(Integer.toString(currentMovie.getReleaseYear()));
        Glide.with(this).load(currentMovie.getImage()).into(image);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("url", currentMovie.getLink());
            startActivity(intent);
            
        });
    }
}