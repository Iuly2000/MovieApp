package com.example.movieapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    UserDAL userDAL = new UserDAL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Button loginButton = findViewById(R.id.loginButtonLogin);
        loginButton.setOnClickListener(this::VerifyUser);
        Button registerButton = findViewById(R.id.registerButtonLogin);
        registerButton.setOnClickListener(this::ChangeToRegister);
    }

    public void VerifyUser(View v) {
        TextView username = findViewById(R.id.usernameTextLogin);
        TextView password = findViewById(R.id.passwordTextLogin);

        if (this.userDAL.VerifiedUser(username.getText().toString(), password.getText().toString())) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            this.finish();
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Username/Password are incorrect!");
            builder1.setNegativeButton(
                    "OK",
                    (dialog, id) -> dialog.cancel());
            AlertDialog loginAlert = builder1.create();
            loginAlert.show();
        }
    }

    public void ChangeToRegister(View v) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

}