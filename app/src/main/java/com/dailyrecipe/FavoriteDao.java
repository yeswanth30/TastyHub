package com.dailyrecipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);


    @Query("SELECT * FROM favorites WHERE userId = :userId")
    LiveData<List<Favorite>> getFavoritesForUser(int userId);
    @Query("SELECT * FROM favorites WHERE userId = :userId AND recipeId = :recipeId")
    Favorite getFavorite(int userId, int recipeId);

    @Query("SELECT * FROM recipes n join favorites f on n.recipeId=f.recipeId")
    List<Recipe> getallfavouriterecipie();


}
