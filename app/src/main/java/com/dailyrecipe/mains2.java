package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class mains2 extends AppCompatActivity {

     SlidingMenuLayout slidingMenuLayout;
     ImageView menuButton;
     ImageView closeMenuButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mains2);

        slidingMenuLayout = findViewById(R.id.sliding_menu_layout);
        menuButton = findViewById(R.id.menu_button);
        closeMenuButton = findViewById(R.id.close_menu_button);

        if (menuButton != null) {
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Your click handling code
                }
            });
        }

        if (closeMenuButton != null) {
            closeMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Your click handling code
                }
            });
        }

    }}
