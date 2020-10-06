package com.example.crime_intent.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crime_intent.datebase.CrimeDAO;
import com.example.crime_intent.datebase.CrimeDataBaseRoom;
import com.example.crime_intent.datebase.UserDBHelper;
import com.example.crime_intent.datebase.UserDBSchema;
import com.example.crime_intent.model.User;
import static  com.example.crime_intent.datebase.UserDBSchema.UserTable.Cols;

import java.util.ArrayList;
import java.util.List;

public class UserDBRepository {
    private static UserDBRepository sInstance;
    private Context mContext;
    private CrimeDAO mUserDAO;
    private SQLiteDatabase mDatabase;


    public static UserDBRepository getInstance(Context context) {
            if (sInstance == null)
                sInstance = new UserDBRepository(context);

            return sInstance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        UserDBHelper userDBHelper = new UserDBHelper(mContext);
        mDatabase = userDBHelper.getWritableDatabase();

    }

    public List<User> getUsers() {
        return mUserDAO.getUsers();
        /*List<User> users = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                UserDBSchema.UserTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return users;

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                User user = extractUserFromCursor(cursor);
                users.add(user);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return users;*/
    }

    public User getUser(String username){
        return mUserDAO.getUser(username);

        /*String where = Cols.USERNAME + " = ?";
        String[] whereArgs = new String[]{username};

        Cursor cursor = mDatabase.query(
                UserDBSchema.UserTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        try {
            cursor.moveToFirst();
            User user = extractUserFromCursor(cursor);

            return user;
        } finally {
            cursor.close();
        }
*/
    }

    public void insertUser(User user) {
       /* ContentValues values = getContentValues(user);
        mDatabase.insert(UserDBSchema.UserTable.NAME, null, values);*/
       mUserDAO.insertUser(user);
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(Cols.PASSWORD, user.getPassword());
        values.put(Cols.USERNAME, user.getUsername());
        return values;
    }

    private User extractUserFromCursor(Cursor cursor) {
        String username = cursor.getString(cursor.getColumnIndex(Cols.USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(Cols.PASSWORD));

        return new User(username, password);
    }


}
