package com.dailyrecipe.dbhelper;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dailyrecipe.Recipe;
import com.dailyrecipe.RecipeDao;
import com.dailyrecipe.User;
import com.dailyrecipe.UserDao;

@Database(entities = {User.class, Recipe.class}, version = 9, exportSchema = false)

public abstract class RecipeDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "recipe_database";
    private static RecipeDatabase instance;




    public abstract UserDao userDao();
    public abstract RecipeDao recipeDao();

    public static synchronized RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
