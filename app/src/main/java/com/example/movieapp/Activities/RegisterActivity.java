package com.example.movieapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.R;

public class RegisterActivity extends AppCompatActivity{
    UserDAL userDAL = new UserDAL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.registerButtonRegister);
        registerButton.setOnClickListener(this::RegisterUser);
    }

    public void RegisterUser(View v){
        TextView username = findViewById(R.id.usernameTextRegister);
        TextView password = findViewById(R.id.passwordTextRegister);
        TextView email = findViewById(R.id.emailTextRegister);

        userDAL.InsertUser(new User(username.getText().toString(),password.getText().toString(),email.getText().toString()));

        this.finish();
    }

}
