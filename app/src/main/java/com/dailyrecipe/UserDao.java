package com.dailyrecipe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);


        @Query("SELECT * FROM Users WHERE userId = :userId")
        User getUserById(int userId);


    @Query("SELECT * FROM users WHERE LOWER(email) = LOWER(:email)")
    User getUserByEmail(String email);


    @Query("SELECT * FROM users")
    List<User> getAllUsers();

}

