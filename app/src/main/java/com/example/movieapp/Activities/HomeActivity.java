package com.example.movieapp.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.DataAccessLevel.MovieDAL;
import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.Helpers.MoviesPagerAdapter;
import com.example.movieapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    private MoviesPagerAdapter moviesPagerAdapter;
    private ViewPager viewPager;
    private List<Movie> moviesList;
    private MovieDAL movieDAL = new MovieDAL();
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.tabLayout = findViewById(R.id.tab_indicator);
        this.moviesList = movieDAL.GetAllMovies();
        this.SetBannerMoviesPagerAdapter(moviesList);
    }

    private void SetBannerMoviesPagerAdapter(List<Movie> movieList) {
        this.viewPager = findViewById(R.id.banner_viewPager);
        this.moviesPagerAdapter = new MoviesPagerAdapter(this, movieList);
        this.viewPager.setAdapter(moviesPagerAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
        Timer sliderTimer=new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(),4000,6000);
        this.tabLayout.setupWithViewPager(viewPager,true);
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < moviesList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                else{
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }
}