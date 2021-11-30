package com.example.movieapp.Activities;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.DataAccessLevel.MovieDAL;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.Helpers.MoviesPagerAdapter;
import com.example.movieapp.Helpers.MoviesRecyclerAdapter;
import com.example.movieapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    private MoviesPagerAdapter moviesPagerAdapter;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private List<Movie> moviesList;
    private List<Movie> bannerMoviesList;
    private MovieDAL movieDAL = new MovieDAL();
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.tabLayout = findViewById(R.id.tab_indicator);
        this.bannerMoviesList = movieDAL.GetTheLast5Movies();
        this.SetBannerMoviesPagerAdapter(bannerMoviesList);
        this.moviesList = movieDAL.GetAllMovies();
        this.SetMoviesRecyclerAdapter(moviesList);
    }

    private void SetBannerMoviesPagerAdapter(List<Movie> movieList) {
        this.viewPager = findViewById(R.id.banner_viewPager);
        this.moviesPagerAdapter = new MoviesPagerAdapter(this, movieList);
        this.viewPager.setAdapter(moviesPagerAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        this.tabLayout.setupWithViewPager(viewPager, true);
    }

    private void SetMoviesRecyclerAdapter(List<Movie> movieList) {
        this.recyclerView = findViewById(R.id.movies_recyclerView);
        this.moviesRecyclerAdapter = new MoviesRecyclerAdapter(this, movieList);
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
}
