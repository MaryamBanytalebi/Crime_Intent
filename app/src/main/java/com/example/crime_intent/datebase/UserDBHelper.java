package com.example.crime_intent.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.crime_intent.datebase.UserDBSchema.CrimeTable.Cols;


import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {


    public UserDBHelper(@Nullable Context context) {
        super(context, UserDBSchema.NAME, null, UserDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sbQuery_user = new StringBuilder();
        sbQuery_user.append("CREATE TABLE " + UserDBSchema.UserTable.NAME + " (");
        sbQuery_user.append(UserDBSchema.UserTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery_user.append(UserDBSchema.UserTable.Cols.USERNAME + " TEXT NOT NULL,");
        sbQuery_user.append(UserDBSchema.UserTable.Cols.PASSWORD + " TEXT");

        db.execSQL(sbQuery_user.toString());

        StringBuilder sbQuery_crime = new StringBuilder();
        sbQuery_crime.append("CREAT TABLE " + UserDBSchema.UserTable.NAME + "(");
        sbQuery_crime.append(Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sbQuery_crime.append(Cols.UUID + " TEXT NOT NULL ");
        sbQuery_crime.append(Cols.TITLE + " TEXT, ");
        sbQuery_crime.append(Cols.DATE + " TEXT, ");
        sbQuery_crime.append(Cols.SOLVED + " INTEGER ");

        db.execSQL(sbQuery_crime.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
