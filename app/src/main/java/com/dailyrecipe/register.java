package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyrecipe.dbhelper.RecipeDatabase;
import com.dailyrecipe.User;
import com.dailyrecipe.UserDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class register extends AppCompatActivity {
    TextView textView4bb;
    EditText nameEditText, emailEditText, passwordEditText;
//    ImageView textView34;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        textView4bb = findViewById(R.id.textView4bb);


        textView4bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent15 = new Intent(register.this, login.class);
                startActivity(intent15);
            }
        });

         nameEditText = findViewById(R.id.fullname34);
         emailEditText = findViewById(R.id.email19);
         passwordEditText = findViewById(R.id.password19);

        ImageView registerButton = findViewById(R.id.textView34);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
//                long times = System.currentTimeMillis();
//                SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
//                String formats = dates.format(new Date(times));

                User newUser = new User(name, email, password, System.currentTimeMillis());

                insertUser(newUser);

                Toast.makeText(register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void insertUser(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = RecipeDatabase.getInstance(register.this).userDao();

                userDao.insertUser(user);
            }
        }).start();
    }
}
