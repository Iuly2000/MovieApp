package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.DataAccessLevel.UserDAL;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.R;

public class AccountSettingsActivity extends AppCompatActivity {

    Button updateButton;
    User currentUser;
    EditText userPassword, userNewPassword, userNewEmail;
    TextView username;
    CheckBox newPasswordCheckBox, emailCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        setCurrentUser();
        this.userPassword = findViewById(R.id.passwordTextAccount);
        this.userNewPassword = findViewById(R.id.newPasswordTextAccount);
        this.userNewEmail = findViewById(R.id.emailTextAccount);
        this.userNewEmail.setText(this.currentUser.getEmail());
        this.username = findViewById(R.id.usernameLabelAccount);
        this.username.setText(this.currentUser.getUsername());
        this.updateButton = findViewById(R.id.updateButtonAccount);
        this.newPasswordCheckBox = findViewById(R.id.add_new_password_checkbox);
        this.emailCheckBox = findViewById(R.id.add_email_checkbox);
        updateButton.setOnClickListener(this::verifyUserChanges);
    }

    private void setCurrentUser() {
        this.currentUser = UserDAL.GetUserById(getIntent().getIntExtra("userID", 0));
    }

    private void verifyUserChanges(View v)
    {
            String password = this.userNewPassword.getText().toString();
            String email = this.userNewEmail.getText().toString();
            if (!this.currentUser.getPassword().equals(this.userPassword.getText().toString())) {
                Toast.makeText(this, "Password is incorrect!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!this.newPasswordCheckBox.isChecked() && !this.emailCheckBox.isChecked()) {
                Toast.makeText(this, "You have to check at least one option!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (password.equals("") && this.newPasswordCheckBox.isChecked()) {
                Toast.makeText(this, "New Password can not be empty!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if(!email.contains("@")){
                Toast.makeText(this, "Email must contain @!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (this.newPasswordCheckBox.isChecked() && this.emailCheckBox.isChecked()) {
                this.currentUser.setPassword(password);
                this.currentUser.setEmail(email);
            } else if (this.newPasswordCheckBox.isChecked()) {
                this.currentUser.setPassword(password);
            } else if (this.emailCheckBox.isChecked()) {
                this.currentUser.setEmail(email);
            }

            UserDAL.updateUser(this.currentUser);
            this.finish();
    }
}