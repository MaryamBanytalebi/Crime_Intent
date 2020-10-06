package com.example.crime_intent.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.crime_intent.datebase.UserDBSchema.*;
import androidx.annotation.Nullable;


public class UserDBHelper extends SQLiteOpenHelper {
    public UserDBHelper(@Nullable Context context) {
        super(context, UserDBSchema.NAME, null, UserDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sbQuery_crime = new StringBuilder();
        sbQuery_crime.append("CREATE TABLE " + CrimeTable.NAME + " (");
        sbQuery_crime.append(CrimeTable.Cols.ID + "INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery_crime.append(CrimeTable.Cols.UUID + "TEXT NOT NULL,");
        sbQuery_crime.append(CrimeTable.Cols.TITLE + "TEXT,");
        sbQuery_crime.append(CrimeTable.Cols.DATE + "TEXT,");
        sbQuery_crime.append(CrimeTable.Cols.SUSPECT + "TEXT,");
        sbQuery_crime.append(CrimeTable.Cols.SUSPECT_PHONE + "TEXT,");
        sbQuery_crime.append(CrimeTable.Cols.SOLVED + "INTEGER");
        sbQuery_crime.append(");");
        db.execSQL(sbQuery_crime.toString());

        StringBuilder sbQuery_user = new StringBuilder();
        sbQuery_user.append("CREATE TABLE " + UserTable.NAME + " (");
        sbQuery_user.append(UserTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery_user.append(UserTable.Cols.USERNAME + " TEXT NOT NULL,");
        sbQuery_user.append(UserTable.Cols.PASSWORD + " TEXT");
        sbQuery_user.append(");");

        db.execSQL(sbQuery_user.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
