package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class mains3 extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View menuContent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mains2);

        drawerLayout = findViewById(R.id.drawer_layout);
      //  menuContent = findViewById(R.id.menu_content);
    }

    public void openMenu(View view) {
        // Calculate the width of the menu content
        int menuWidth = menuContent.getWidth();

        // Slide the menu content from left to right
        TranslateAnimation animation = new TranslateAnimation(-menuWidth, 20, 30, 50);
        animation.setDuration(500);
        menuContent.startAnimation(animation);

        // Open the drawer layout to reveal the menu
        drawerLayout.openDrawer(menuContent);
    }

    public void closeMenu(View view) {
        // Calculate the width of the menu content
        int menuWidth = menuContent.getWidth();

        // Slide the menu content from right to left
        TranslateAnimation animation = new TranslateAnimation(40, -menuWidth, 10, 50);
        animation.setDuration(300);
        menuContent.startAnimation(animation);

        // Close the drawer layout to hide the menu
        drawerLayout.closeDrawer(menuContent);
    }
}
