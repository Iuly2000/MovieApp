package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.R;

public class AccountSettingsActivity extends AppCompatActivity {

    Button updateButton;
    User currentUser;
    UserDAL userDAL= new UserDAL();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        SetCurrentUser();
        updateButton.setOnClickListener(this::VerifyUser);
    }

    private void SetCurrentUser() {
        this.currentUser = new User(
                getIntent().getIntExtra("userID", 0),
                getIntent().getStringExtra("username"),
                getIntent().getStringExtra("password"),
                getIntent().getStringExtra("email")
        );
    }
}