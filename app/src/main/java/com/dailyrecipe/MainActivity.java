package com.dailyrecipe;

import android.content.Intent;
import android.os.Bundle;

import com.dailyrecipe.dbhelper.sharedpreference;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dailyrecipe.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
ImageView textView3;
TextView textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView3=findViewById(R.id.textView37);
        textView4=findViewById(R.id.textView4);
        if (isUserLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, mains.class);
            startActivity(intent);
            finish();
        }

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent13 = new Intent(MainActivity.this, register.class);
                startActivity(intent13);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent14 = new Intent(MainActivity.this, login.class);
                startActivity(intent14);
            }
        });
    }

    private boolean isUserLoggedIn() {
        String userId = sharedpreference.getSharedprefrences(this, "userId");

        return !userId.isEmpty();
    }
}
