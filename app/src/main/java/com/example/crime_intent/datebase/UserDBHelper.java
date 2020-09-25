package com.example.crime_intent.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {


    public UserDBHelper(@Nullable Context context) {
        super(context, UserDBSchema.NAME, null, UserDBSchema.VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("CREATE TABLE " + UserDBSchema.UserTable.NAME + " (");
        sbQuery.append(UserDBSchema.UserTable.Cols.USERNAME + " TEXT,");
        sbQuery.append(UserDBSchema.UserTable.Cols.PASSWORD + " TEXT,");

        db.execSQL(sbQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
