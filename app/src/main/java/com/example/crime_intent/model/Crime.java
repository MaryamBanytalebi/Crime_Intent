package com.example.crime_intent.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;
@Entity(tableName = "crimeTable")
public class Crime {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long primaryId;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "uuid")
    public UUID mUUID;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "solve")
    private boolean solved;

    @ColumnInfo(name = "suspect")
    private String mSuspect;

    @ColumnInfo(name = "phone")
    private String mSuspectPhoneNumber;

    private boolean mCheckSelect;

    public Crime(String title, String description,Date date) {
        mTitle = title;
        mDescription = description;
        mUUID=UUID.randomUUID();
        solved =true;
        mDate=date;
    }

    public Crime() {
        this(UUID.randomUUID());
        //mDate=new Date();
    }

    public Crime(UUID UUID) {
        mUUID = UUID;
        mDate = new Date();
        mCheckSelect = false;
    }

    public Crime( UUID UUID,  String title, Date date, boolean solved,String suspect, String suspectPhoneNumber) {
        mDate = date;
        mUUID = UUID;
        mTitle = title;
        this.solved = solved;
        mCheckSelect = false;
        mSuspect = suspect;
        mSuspectPhoneNumber = suspectPhoneNumber;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isCheckSelect() {
        return mCheckSelect;
    }

    public void setCheckSelect(boolean checkSelect) {
        mCheckSelect = checkSelect;
    }

    public String getSuspectPhoneNumber() {
        return mSuspectPhoneNumber;
    }

    public void setSuspectPhoneNumber(String suspectPhoneNumber) {
        mSuspectPhoneNumber = suspectPhoneNumber;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

}
