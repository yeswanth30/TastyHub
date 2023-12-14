package com.dailyrecipe;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dash_board);

        // Initialize your recycler views and adapters here

        // Set click listener for the menu button
        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the menu visibility
                toggleMenu();
            }
        });

        // Set click listener for the close menu button
        ImageView closeMenuButton = findViewById(R.id.close_menu);
        closeMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the menu
                closeMenu();
            }
        });

        // Initialize and set up your recycler views and adapters
        // ...

        // Initialize and set up your dynamic recycler view and adapter
        // ...
    }

    private void toggleMenu() {
        // Toggle the menu visibility
        // You can show/hide the menu layout or apply any animations here
        View sideMenu = findViewById(R.id.side_menu);
        if (sideMenu.getVisibility() == View.VISIBLE) {
            sideMenu.setVisibility(View.GONE);
        } else {
            sideMenu.setVisibility(View.VISIBLE);
        }
    }

    private void closeMenu() {
        // Close the menu
        // You can hide the menu layout or apply any animations here
        View sideMenu = findViewById(R.id.side_menu);
        sideMenu.setVisibility(View.GONE);
    }
}
