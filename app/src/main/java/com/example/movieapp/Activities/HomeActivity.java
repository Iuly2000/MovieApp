package com.example.movieapp.Activities;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.DataAccessLevel.CategoryDAL;
import com.example.movieapp.DataAccessLevel.FavoritesMoviesDAL;
import com.example.movieapp.DataAccessLevel.MovieCategoriesDAL;
import com.example.movieapp.DataAccessLevel.MovieDAL;
import com.example.movieapp.EntityLevel.Category;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.Helpers.MoviesPagerAdapter;
import com.example.movieapp.Helpers.MoviesRecyclerAdapter;
import com.example.movieapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    private MoviesPagerAdapter moviesPagerAdapter;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private List<Movie> allMoviesList;
    private List<Movie> bannerMoviesList;
    private final MovieDAL movieDAL = new MovieDAL();
    private final CategoryDAL categoryDAL = new CategoryDAL();
    private final MovieCategoriesDAL movieCategoriesDAL = new MovieCategoriesDAL();
    private final FavoritesMoviesDAL favoritesMoviesDAL = new FavoritesMoviesDAL();
    private HomeActivity context;

    private TabLayout tabLayout;
    private Spinner categorySpinner, userOptionsSpinner;
    private ImageView searchIcon;
    private EditText searchText;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.SetFindViewByID();
        this.SetCurrentUser();
        this.context=this;
        this.bannerMoviesList = movieDAL.GetTheLast5Movies();
        this.allMoviesList = movieDAL.GetAllMovies();
        this.SetBannerMoviesPagerAdapter(bannerMoviesList);
        this.SetMoviesRecyclerAdapter(allMoviesList);

        this.SetCategorySpinner(categoryDAL.GetAllCategoriesNames());
       this.SetUserOptionsSpinner();

        this.OnItemSelectedUserOptionsSpinner();
        this.OnItemSelectedCategorySpinner();
        this.OnSearchIconClick();

    }

    private void OnItemSelectedCategorySpinner(){
        this.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    SetMoviesRecyclerAdapter(allMoviesList);
                } else {
                    SetMoviesRecyclerAdapter(movieCategoriesDAL.GetMoviesByCategory(categorySpinner.getSelectedItem().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void OnSearchIconClick(){
        this.searchIcon.setOnClickListener(v -> {
            String word = this.searchText.getText().toString().toUpperCase();
            if (!word.equals("")) {
                this.SetMoviesRecyclerAdapter(this.movieDAL.SearchMoviesByWord(word));
            }
        });
    }

    private void OnItemSelectedUserOptionsSpinner(){
        this.userOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent= new Intent(context, AccountSettingsActivity.class);
                        intent.putExtra("userID", currentUser.getUserID());
                        intent.putExtra("username", currentUser.getUsername());
                        intent.putExtra("password", currentUser.getPassword());
                        intent.putExtra("email", currentUser.getEmail());
                        startActivity(intent);
                        userOptionsSpinner.setSelection(3);
                        break;
                    case 1:
                        SetMoviesRecyclerAdapter(favoritesMoviesDAL.GetFavoriteMoviesForUser(currentUser.getUserID()));
                        userOptionsSpinner.setSelection(3);
                        break;
                    case 2:
                        finish();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void SetFindViewByID(){
        this.tabLayout = findViewById(R.id.tab_indicator);
        this.categorySpinner = findViewById(R.id.category_spinner);
        this.userOptionsSpinner = findViewById(R.id.user_options_spinner);
        this.searchIcon = findViewById(R.id.search_icon);
        this.searchText = findViewById(R.id.search_text);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setPositiveButton("Yes", (dialog, id) -> finishAffinity())
                .setNegativeButton("No", (dialog, id) -> dialog.cancel()).show();
    }


    private void SetCategorySpinner(ArrayList<String> categoriesNames) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesNames);
        categoriesNames.add(0, "All");
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);
    }

    private void SetUserOptionsSpinner(){

        String[] user_options = getResources().getStringArray(R.array.user_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, user_options){
            @Override
            public int getCount() {
                return(user_options.length-1);
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.userOptionsSpinner.setAdapter(adapter);
    }

    private void SetBannerMoviesPagerAdapter(List<Movie> movieList) {
        this.viewPager = findViewById(R.id.banner_viewPager);
        this.moviesPagerAdapter = new MoviesPagerAdapter(this, movieList,this.currentUser.getUserID());
        this.viewPager.setAdapter(moviesPagerAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        this.tabLayout.setupWithViewPager(viewPager, true);
    }

    private void SetMoviesRecyclerAdapter(List<Movie> movieList) {
        this.recyclerView = findViewById(R.id.movies_recyclerView);
        this.moviesRecyclerAdapter = new MoviesRecyclerAdapter(this, movieList,this.currentUser.getUserID());
        recyclerView.setAdapter(moviesRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < bannerMoviesList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }

    private void SetCurrentUser() {
        currentUser = new User(
                getIntent().getIntExtra("userID", 0),
                getIntent().getStringExtra("username"),
                getIntent().getStringExtra("password"),
                getIntent().getStringExtra("email")
        );
    }
}
