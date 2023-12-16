package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailyrecipe.dbhelper.RecipeDatabase;

import java.util.List;

public class favourites extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    RecyclerView recyclerView1223876;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        recyclerView1223876=findViewById(R.id.recyclerView1223876);

        getallfavouriterecipie();



    }
    private void getallfavouriterecipie() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FavoriteDao userDao = RecipeDatabase.getInstance(favourites.this).favoriteDao();
                List<Recipe> favoriteRecipes = userDao.getallfavouriterecipie();


                Log.d("favourites", "Number of favorite recipes: " + favoriteRecipes.size());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set up the RecyclerView
                        FavouriteRecipeAdapter recipeAdapter = new FavouriteRecipeAdapter(favoriteRecipes);
                        recyclerView1223876.setLayoutManager(new LinearLayoutManager(favourites.this));
                        recyclerView1223876.setAdapter(recipeAdapter);
                        recipeAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}




