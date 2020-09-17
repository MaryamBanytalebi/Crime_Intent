package com.example.crime_intent.model;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private Date mDate;
    private UUID mUUID;
    private String mTitle;
    private String mDescription;
    private boolean solved;

    public Crime(String title, String description,Date date) {
        mTitle = title;
        mDescription = description;
        mUUID=UUID.randomUUID();
        solved =true;
        mDate=date;
    }

    public Crime() {
        mUUID=UUID.randomUUID();
        mDate=new Date();
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
}
