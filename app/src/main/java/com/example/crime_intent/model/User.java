package com.example.crime_intent.model;

import java.util.UUID;

public class User {
    private String mUsername;
    private String mPassword;

    public User(String password, String username) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

}
