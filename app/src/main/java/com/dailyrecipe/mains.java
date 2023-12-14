package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.dailyrecipe.R;
import com.dailyrecipe.Recipe;
import com.dailyrecipe.Recipe2Adapter;
import com.dailyrecipe.RecipeAdapter;
import com.dailyrecipe.RecipeDao;
import com.dailyrecipe.dbhelper.RecipeDatabase;
import com.dailyrecipe.favourites;
import com.dailyrecipe.insert;
import com.dailyrecipe.login;
import com.dailyrecipe.recentlyviewed;

import java.lang.ref.WeakReference;
import java.util.List;

public class mains extends AppCompatActivity {

    DrawerLayout drawerLayout;
    int userid;
    TextView textView67, textView39, textView456, logout, textView1331,firstTextView;
    static RecyclerView recyclerView;
    static RecyclerView recyclerViewForRecipe2; // New RecyclerView
    RecipeAdapter recipeAdapter;
    Recipe2Adapter recipe2Adapter; // New Adapter

    UserDao userDao;

    Integer userids;

    private static final String PREF_NAME = "my_preferences";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mains);

        textView67 = findViewById(R.id.textView67);
        textView39 = findViewById(R.id.textView395);
        textView456 = findViewById(R.id.textView456);
        logout = findViewById(R.id.textView5687);
        recyclerView = findViewById(R.id.recyclerView1256);
        recyclerViewForRecipe2 = findViewById(R.id.recyclerView1223);
        textView1331 = findViewById(R.id.textView1331);
        firstTextView=findViewById(R.id.firstTextView);

        drawerLayout = findViewById(R.id.drawer_layout);

        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userid = sharedpreferences.getInt("userid", -1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewForRecipe2.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your UserDao using Room
        RecipeDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                RecipeDatabase.class,
                "recipe_database"
        ).build();
        userDao = db.userDao();

        new DatabaseTask(getApplicationContext()).execute();

        textView456.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent15 = new Intent(mains.this, recentlyviewed.class);
                startActivity(intent15);
            }
        });

        textView67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent15 = new Intent(mains.this, insert.class);
                startActivity(intent15);
            }
        });

        textView39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent153 = new Intent(mains.this, favourites.class);
                startActivity(intent153);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        new LoadUserDataTask(userid, textView1331, firstTextView, userDao).execute();

    }

    private static class DatabaseTask extends AsyncTask<Void, Void, List<Recipe>> {
        private Context context;

        public DatabaseTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            RecipeDatabase db = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RecipeDatabase.class,
                    "recipe_database"
            ).build();
            RecipeDao recipeDao = db.recipeDao();
            return recipeDao.getAllRecipes();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            // first RecyclerView
            RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
            recyclerView.setAdapter(recipeAdapter);

            // second RecyclerView
            Recipe2Adapter recipe2Adapter = new Recipe2Adapter(recipes);
            recyclerViewForRecipe2.setAdapter(recipe2Adapter);
        }

    }

    // Method to open the side menu
    public void openSideMenu(View view) {
        drawerLayout.openDrawer(GravityCompat.START); // Open the drawer from the start (left) side
    }

    // Method to close the side menu
    public void closeSideMenu(View view) {
        drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer from the start (left) side
    }

    // Method to show the logout confirmation dialog
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        performLogout();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No, do nothing
                    }
                });
        builder.create().show();
    }

    // Method to perform the logout action
    private void performLogout() {
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent34 = new Intent(mains.this, login.class);
        intent34.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent34);
        finish();
    }

    static class LoadUserDataTask extends AsyncTask<Void, Void, Pair<String, String>> {
        private final int userId;
        private final WeakReference<TextView> textView1331WeakReference;
        private final WeakReference<TextView> firstTextViewWeakReference;
        private final UserDao userDao;

        LoadUserDataTask(int userId, TextView textView1331, TextView firstTextView, UserDao userDao) {
            this.userId = userId;
            this.textView1331WeakReference = new WeakReference<>(textView1331);
            this.firstTextViewWeakReference = new WeakReference<>(firstTextView);
            this.userDao = userDao;
        }

        @Override
        protected Pair<String, String> doInBackground(Void... voids) {
            try {
                User user = userDao.getUserById(userId);
                if (user != null) {
                    String fullName = user.getFullName();
                    String otherInfo = user.getFullName();
                    return new Pair<>(fullName, otherInfo);
                } else {
                    return null;
                }
            } catch (Exception e) {
                Log.e("LoadUserDataTask", "Error fetching user data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Pair<String, String> userData) {
            super.onPostExecute(userData);

            TextView textView1331 = textView1331WeakReference.get();
            TextView firstTextView = firstTextViewWeakReference.get();

            if (textView1331 != null && firstTextView != null && userData != null) {
                textView1331.setText(userData.first);
                firstTextView.setText(userData.second);
            }
        }
    }
}




