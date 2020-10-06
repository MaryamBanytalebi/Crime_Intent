package com.example.crime_intent.datebase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.crime_intent.model.Crime;
import com.example.crime_intent.model.User;

@Database(entities = {Crime.class, User.class}, version = 1)

@TypeConverters({Converter.class})
public abstract class CrimeDataBaseRoom extends RoomDatabase {

    public abstract CrimeDAO getCrimeDatabaseDao();
}
