package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.DataAccessLevel.FavoritesMoviesDAL;
import com.example.movieapp.DataAccessLevel.MovieCategoriesDAL;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.R;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    private TextView movieDescription, ratingText, releaseText, movieName, categoriesNames;
    private ImageView image;
    private Button playButton;
    private Movie currentMovie;
    private CheckBox favoriteMovieCheckbox;
    private int userID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        this.setFindViewByID();
        this.setCurrentMovie();
        this.userID=getIntent().getIntExtra("userID", 0);
        this.favoriteMovieCheckbox.setChecked(FavoritesMoviesDAL.
                checkIfFavorite(userID,currentMovie.getMovie_id()));
        onCheckBoxClick();
        this.categoriesNames.setText(this.setCategoriesNames
                (MovieCategoriesDAL.getCategoriesNamesForMovie(currentMovie.getMovie_id())));
        this.movieName.setText(currentMovie.getName());
        this.movieDescription.setText(currentMovie.getDescription());
        this.ratingText.setText(Float.toString(currentMovie.getRating()));
        this.releaseText.setText(Integer.toString(currentMovie.getReleaseYear()));
        Glide.with(this).load(currentMovie.getImage()).into(image);

        this.playButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("url", currentMovie.getLink());
            startActivity(intent);

        });
    }
    private void onCheckBoxClick(){
        this.favoriteMovieCheckbox.setOnClickListener(v->{
            if(this.favoriteMovieCheckbox.isChecked()){
                FavoritesMoviesDAL.insertFavoriteMovie(this.userID,this.currentMovie.getMovie_id());
            }
            else
            {
                FavoritesMoviesDAL.deleteFavoriteMovie(this.userID,this.currentMovie.getMovie_id());
            }
        });
    }

    private String setCategoriesNames(ArrayList<String> categoriesNames) {
        StringBuilder categoriesNamesString = new StringBuilder();
        for (String categoryName : categoriesNames) {
            categoriesNamesString.append(categoryName).append(", ");
        }
        categoriesNamesString.
                delete(categoriesNamesString.length() - 2, categoriesNamesString.length() - 1);
        return categoriesNamesString.toString();
    }

    private void setCurrentMovie() {
        currentMovie = new Movie(
                getIntent().getIntExtra("movieID", 0),
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("description"),
                getIntent().getStringExtra("image"),
                getIntent().getStringExtra("link"),
                getIntent().getIntExtra("releaseYear", 0),
                getIntent().getFloatExtra("rating", 0)
        );
    }
    private void setFindViewByID(){
        this.movieName = findViewById(R.id.movie_name_label);
        this.movieDescription = findViewById(R.id.movie_description);
        this.ratingText = findViewById(R.id.ratingText);
        this.releaseText = findViewById(R.id.releaseText);
        this.image = findViewById(R.id.image);
        this.playButton = findViewById(R.id.playButton);
        this.categoriesNames = findViewById(R.id.categories_text);
        this.favoriteMovieCheckbox=findViewById(R.id.add_to_favorite_checkbox);
    }
}