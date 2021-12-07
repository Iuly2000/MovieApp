package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.movieapp.DataAccessLevel.CategoryDAL;
import com.example.movieapp.DataAccessLevel.FavoritesMoviesDAL;
import com.example.movieapp.DataAccessLevel.MovieCategoriesDAL;
import com.example.movieapp.DataAccessLevel.MovieDAL;
import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.Helpers.MoviesPagerAdapter;
import com.example.movieapp.Helpers.MoviesRecyclerAdapter;
import com.example.movieapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Movie> allMoviesList;
    private List<Movie> bannerMoviesList;
    private int spinnerCheck;

    private TabLayout tabLayout;
    private Spinner categorySpinner, userOptionsSpinner;
    private ImageView searchIcon;
    private EditText searchText;
    private User currentUser;
    String[] user_options;
    ArrayList<String> categoriesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user_options = getResources().getStringArray(R.array.user_options);
        categoriesNames=CategoryDAL.getAllCategoriesNames();
        this.setFindViewByID();
        this.setCurrentUser();
        this.spinnerCheck = 0;
        this.bannerMoviesList = MovieDAL.getTheLast5Movies();
        this.allMoviesList = MovieDAL.getAllMovies();
        this.setBannerMoviesPagerAdapter(bannerMoviesList);
        this.setMoviesRecyclerAdapter(allMoviesList);

        this.setCategorySpinner();
        this.setUserOptionsSpinner();

        this.onItemSelectedUserOptionsSpinner();
        this.onItemSelectedCategorySpinner();
        this.onSearchIconClick();

    }

    private void onItemSelectedCategorySpinner() {
        this.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==categoriesNames.size()-1){
                    return;
                }
                if (position == 0) {
                    setMoviesRecyclerAdapter(allMoviesList);
                } else {
                    setMoviesRecyclerAdapter(MovieCategoriesDAL.getMoviesByCategory(categorySpinner.getSelectedItem().toString()));
                }

                categorySpinner.setSelection(categoriesNames.size()-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onSearchIconClick() {
        this.searchIcon.setOnClickListener(v -> {
            String word = this.searchText.getText().toString().toUpperCase();
            if (!word.equals("")) {
                this.setMoviesRecyclerAdapter(MovieDAL.searchMoviesByWord(word));
            }
        });
    }

    private void onItemSelectedUserOptionsSpinner() {
        this.userOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCheck += 1;
                if (spinnerCheck == 1) {
                    userOptionsSpinner.setSelection(user_options.length - 1);
                    return;
                }
                    switch (position) {
                        case 0:
                            currentUser = UserDAL.GetUserById(currentUser.getUserID());
                            Intent intent = new Intent(HomeActivity.this, AccountSettingsActivity.class);
                            intent.putExtra("userID", currentUser.getUserID());
                            startActivity(intent);
                            break;
                        case 1:
                            setMoviesRecyclerAdapter(FavoritesMoviesDAL.getFavoriteMoviesForUser(currentUser.getUserID()));
                            break;
                        case 2:
                            finish();
                            break;
                        default:
                            break;
                    }

                userOptionsSpinner.setSelection(user_options.length - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setFindViewByID() {
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


    private void setCategorySpinner() {
        categoriesNames=CategoryDAL.getAllCategoriesNames();
        assert categoriesNames != null;
        categoriesNames.add(0, "All");
        categoriesNames.add("None");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesNames){
            @Override
            public int getCount() {
                return (categoriesNames.size() - 1);
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);
    }

    private void setUserOptionsSpinner() {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, user_options) {
            @Override
            public int getCount() {
                return (user_options.length - 1);
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.userOptionsSpinner.setAdapter(adapter);
    }

    private void setBannerMoviesPagerAdapter(List<Movie> movieList) {
        this.viewPager = findViewById(R.id.banner_viewPager);
        MoviesPagerAdapter moviesPagerAdapter = new MoviesPagerAdapter(this, movieList, this.currentUser.getUserID());
        this.viewPager.setAdapter(moviesPagerAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        this.tabLayout.setupWithViewPager(viewPager, true);
    }

    private void setMoviesRecyclerAdapter(List<Movie> movieList) {
        RecyclerView recyclerView = findViewById(R.id.movies_recyclerView);
        MoviesRecyclerAdapter moviesRecyclerAdapter = new MoviesRecyclerAdapter(this, movieList, this.currentUser.getUserID());
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

    private void setCurrentUser() {
        this.currentUser = UserDAL.GetUserById(getIntent().getIntExtra("userID", 0));
    }
}
