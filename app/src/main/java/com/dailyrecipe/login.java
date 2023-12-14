package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import com.dailyrecipe.dbhelper.RecipeDatabase;
import com.dailyrecipe.User;
import com.dailyrecipe.UserDao;

public class login extends AppCompatActivity {
    TextView textView4b;
    ImageView textView3;
    EditText emailEditText, passwordEditText;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        textView4b = findViewById(R.id.textView4bc);
        textView3 = findViewById(R.id.textView39);
        emailEditText = findViewById(R.id.email190);
        passwordEditText = findViewById(R.id.password190);

        textView4b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent16 = new Intent(login.this, register.class);
                startActivity(intent16);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    showToast("Please enter values");
                } else {
                    authenticateUser(email, password);
                    showToast("Successfully login");
                }
            }
        });
    }
    private void authenticateUser(String email, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = RecipeDatabase.getInstance(login.this).userDao();
                User user = userDao.getUserByEmail(email);

                if (user != null) {
                    if (user.getPassword().equals(password)) {

                        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                        SharedPreferences.Editor myedit = sharedPreferences.edit();
                        myedit.putInt("userid", user.getUserId());
                        myedit.commit();

                        Intent intent = new Intent(login.this, mains.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast("Invalid credentials");
                    }
                } else {
                    showAlert("Invalid credentials");
                }
            }
        }).start();
    }
    private void showAlert(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(login.this)
                        .setTitle("Authentication Error")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
    }
    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


