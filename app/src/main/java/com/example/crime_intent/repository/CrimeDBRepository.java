package com.example.crime_intent.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crime_intent.datebase.UserDBHelper;
import com.example.crime_intent.datebase.UserDBSchema;
import com.example.crime_intent.model.Crime;
import static com.example.crime_intent.datebase.UserDBSchema.CrimeTable.Cols;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeDBRepository implements IRepository {

    private static CrimeDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static CrimeDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CrimeDBRepository(context);

        return sInstance;
    }

    public CrimeDBRepository(Context context) {
        UserDBHelper userDBHelper = new UserDBHelper(mContext);
        mDatabase = userDBHelper.getWritableDatabase();
    }

    @Override
    public List<Crime> getList(){
        List<Crime> crimes = new ArrayList<>();
        Cursor cursor = mDatabase.query(UserDBSchema.CrimeTable.NAME,
                null,
                null,
                null,
                null,
                null,null);
        if (cursor == null || cursor.getCount() == 0)
        return crimes;
        try{
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            Crime crime = extractCrimeToCursor(cursor);
            crimes.add(crime);

        cursor.moveToNext();
        }
        }finally {
            cursor.close();
        }

        return null;
    }


    @Override
    public Crime get(UUID id) {
        String where = Cols.UUID + "=?";
        String[] whereArgs = new String[]{id.toString()};
         Cursor cursor = mDatabase.query(UserDBSchema.CrimeTable.NAME,
                 null,
                 where,
                 whereArgs ,
                 null,
                 null,
                 null);
         if (cursor == null || cursor.getCount() == 0)
             return null;

         try {
             cursor.moveToFirst();
             Crime crime = extractCrimeToCursor(cursor);
             return crime;

         } finally {
             cursor.close();
         }
    }

    @Override
    public void insert(Crime crime) {
        ContentValues values = getContentValue(crime);
        mDatabase.insert(UserDBSchema.CrimeTable.NAME,null,null);

    }

    private ContentValues getContentValue(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID , crime.getUUID().toString());
        values.put(Cols.TITLE , crime.getTitle());
        values.put(Cols.DATE , crime.getDate().getTime());
        values.put(Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    private Crime extractCrimeToCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(Cols.UUID)));
        String title = cursor.getString(cursor.getColumnIndex(Cols.TITLE));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(Cols.DATE)));
        boolean solved = cursor.getInt(cursor.getColumnIndex(Cols.SOLVED)) == 0 ? false : true;

        return new Crime(uuid,title,date,solved);
    }

    @Override
    public void insertList(List<Crime> eList) {

    }

    @Override
    public void delete(Crime crime) {
        String whereClause = Cols.UUID +" =? ";
        String[] whereArgs = new String[]{crime.getUUID().toString()};
        mDatabase.delete(UserDBSchema.CrimeTable.NAME,whereClause,whereArgs);

    }

    @Override
    public void update(Crime crime) {
        ContentValues values = new ContentValues();
        String whereClause = Cols.UUID + " = ?" ;
        String[] whereArgs = new String[]{crime.getUUID().toString()};
        mDatabase.update(UserDBSchema.CrimeTable.NAME,values,whereClause,whereArgs);

    }

    @Override
    public int getPosition(UUID id) {
        List<Crime> crimes = getList();
        for (int i = 0; i <crimes.size() ; i++) {
            if (crimes.get(i).getUUID().equals(id));
            return i;
        }
        return -1;
    }
}
