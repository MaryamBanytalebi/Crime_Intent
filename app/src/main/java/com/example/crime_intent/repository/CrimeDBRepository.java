package com.example.crime_intent.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import com.example.crime_intent.datebase.CrimeDAO;
import com.example.crime_intent.datebase.CrimeDataBaseRoom;
import com.example.crime_intent.datebase.UserDBSchema;
import com.example.crime_intent.model.Crime;
import com.example.crime_intent.datebase.UserDBSchema.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeDBRepository implements IRepository {

    private static CrimeDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private CrimeDAO mCrimeDAO;

    public static CrimeDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CrimeDBRepository(context);

        return sInstance;
    }

    public CrimeDBRepository(Context context) {
        mContext = context.getApplicationContext();
       /* UserDBHelper userDBHelper = new UserDBHelper(mContext);
        mDatabase = userDBHelper.getWritableDatabase();*/
        CrimeDataBaseRoom crimeDataBaseRoom = Room.databaseBuilder(mContext,CrimeDataBaseRoom.class
                ,"crime.db")
                .allowMainThreadQueries()
                .build();
        mCrimeDAO = crimeDataBaseRoom.getCrimeDatabaseDao();
    }

    @Override
    public List<Crime> getList(){
        /*List<Crime> crimes = new ArrayList<>();
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

        return null;*/
        return mCrimeDAO.getList();
    }


    @Override
    public Crime get(UUID id) {
       /* String where = Cols.UUID + "=?";
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
         }*/
       return mCrimeDAO.get(id);
    }

    @Override
    public void insert(Crime crime) {
        /*ContentValues values = getContentValue(crime);
        mDatabase.insert(UserDBSchema.CrimeTable.NAME,null,null);
      */
        mCrimeDAO.insert(crime);

    }

    @Override
    public void delete(Crime crime) {
       /* String whereClause = Cols.UUID +" =? ";
        String[] whereArgs = new String[]{crime.getUUID().toString()};
        mDatabase.delete(UserDBSchema.CrimeTable.NAME,whereClause,whereArgs);*/
        mCrimeDAO.delete(crime);

    }

    @Override
    public void update(Crime crime) {
        /*ContentValues values = new ContentValues();
        String whereClause = Cols.UUID + " = ?" ;
        String[] whereArgs = new String[]{crime.getUUID().toString()};
        mDatabase.update(UserDBSchema.CrimeTable.NAME,values,whereClause,whereArgs);*/
        mCrimeDAO.delete(crime);


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

    private ContentValues getContentValue(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID , crime.getUUID().toString());
        values.put(CrimeTable.Cols.TITLE , crime.getTitle());
        values.put(CrimeTable.Cols.DATE , crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    private Crime extractCrimeToCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(CrimeTable.Cols.UUID)));
        String title = cursor.getString(cursor.getColumnIndex(CrimeTable.Cols.TITLE));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(CrimeTable.Cols.DATE)));
        boolean solved = cursor.getInt(cursor.getColumnIndex(CrimeTable.Cols.SOLVED)) == 0 ? false : true;
        String suspect = cursor.getString(cursor.getColumnIndex(CrimeTable.Cols.SUSPECT));
        String suspectPhone = cursor.getString(cursor.getColumnIndex(CrimeTable.Cols.SUSPECT_PHONE));

        return new Crime(uuid,title,date,solved,suspect,suspectPhone);
    }

    @Override
    public void insertList(List<Crime> eList) {

    }

}
