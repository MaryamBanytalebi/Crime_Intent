package com.example.crime_intent.datebase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crime_intent.model.Crime;
import com.example.crime_intent.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface CrimeDAO {
    @Update
     void update(Crime crime);

    @Insert
     void insert(Crime crime);

    @Delete
     void delete(Crime crime);

    @Query("SELECT * FROM crimeTable")
    List<Crime> getList();

    @Query("SELECT * FROM crimeTable WHERE uuid =:inputId")
    Crime get(UUID inputId);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE  username=:name")
    User getUser(String name);
}
