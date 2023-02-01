package com.example.movieapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.registerButtonRegister);
        registerButton.setOnClickListener(this::registerUser);
    }

    public void registerUser(View v) {
        TextView username = findViewById(R.id.usernameTextRegister);
        TextView password = findViewById(R.id.passwordTextRegister);
        TextView confirmPassword = findViewById(R.id.passwordConfirmTextRegister);
        TextView email = findViewById(R.id.emailTextRegister);
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String confirmPasswordString = confirmPassword.getText().toString();
        String emailString = email.getText().toString();

        if (UserDAL.GetUserByUsername(usernameString) != null) {
            Toast.makeText(this, "Username already taken!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(usernameString.equals("")){
            Toast.makeText(this, "Username can not be empty!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(passwordString.length()<6){
            Toast.makeText(this, "Password must at least have 6 characters!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(!passwordString.equals(confirmPasswordString)){
            Toast.makeText(this, "The passwords do not match!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(!emailString.contains("@")){
            Toast.makeText(this, "Email must contain @!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        UserDAL.insertUser(new User(usernameString, passwordString, emailString));

        this.finish();
    }


}
