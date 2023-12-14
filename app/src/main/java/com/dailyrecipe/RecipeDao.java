package com.dailyrecipe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    long insertRecipe(Recipe recipe);


    @Query("SELECT * FROM recipes WHERE userId = :userId")
    List<Recipe> getRecipesByUserId(int userId);



    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
    Recipe getRecipeById(int recipeId);



    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();


    @Query("SELECT description FROM recipes WHERE recipeId = :recipeId")
     String getRecipeDescriptionById(int recipeId);



}
