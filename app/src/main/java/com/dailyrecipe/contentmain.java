package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.dailyrecipe.dbhelper.RecipeDatabase;
import com.dailyrecipe.User;
import com.dailyrecipe.UserDao;

public class contentmain extends AppCompatActivity {
    ImageView myImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        myImageView = findViewById(R.id.myImageView);

        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseOperationTask().execute();
            }
        });
    }

    private class DatabaseOperationTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... voids) {
            try {
                RecipeDatabase appDatabase = Room.databaseBuilder(contentmain.this, RecipeDatabase.class, "recipe_database").build();

                SharedPreferences sharedpreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                int userId = sharedpreferences.getInt("userid", -1);

                UserDao userDao = appDatabase.userDao();

                Log.d("contentmain", "User ID from SharedPreferences: " + userId);

                if (userDao != null) {
                    Log.d("contentmain", "UserDao is not null");
                    return userDao.getUserById(userId);
                } else {
                    Log.e("contentmain", "UserDao is null");
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("contentmain", "An error occurred: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                Intent intent26 = new Intent(contentmain.this, mains.class);
                startActivity(intent26);
            } else {
                Intent intent15 = new Intent(contentmain.this, MainActivity.class);
                startActivity(intent15);
            }
        }
    }
}
