package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.R;

public class LoginActivity extends AppCompatActivity {
    Button loginButton,registerButton;
    private ProgressDialog pd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         loginButton = findViewById(R.id.loginButtonLogin);
         registerButton = findViewById(R.id.registerButtonLogin);
         loginButton.setOnClickListener(this::verifyUser);
         registerButton.setOnClickListener(this::changeToRegister);
    }


    public void verifyUser(View v) {
        TextView username = findViewById(R.id.usernameTextLogin);
        TextView password = findViewById(R.id.passwordTextLogin);
        User currentUser = UserDAL.verifiedUser(username.getText().toString(), password.getText().toString());
        if (currentUser!=null) {
            this.pd = ProgressDialog.show(this, "Movie Up!",
                    "Loading...Please wait...", true, false);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("userID", currentUser.getUserID());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username/Password are incorrect!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void changeToRegister(View v) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.pd!=null && this.pd.isShowing()){

            this.pd.dismiss();
        }
    }
}