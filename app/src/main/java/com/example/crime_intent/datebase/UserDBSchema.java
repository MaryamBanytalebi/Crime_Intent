package com.example.crime_intent.datebase;

public class UserDBSchema {
    public static final String NAME = "user.db";
    public static final Integer VERSION = 1;

    public static final class CrimeTable{
        public static final String NAME = "crimeTable";

        public static final class Cols{
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String TITLE = "title";
            public static final String SUSPECT = "suspect";
            public static final String SUSPECT_PHONE = "suspect_phone";
        }
    }

    public static final class UserTable{
        public static final String NAME = "userTable";
        public static final class Cols{
            public static final String ID = "id";
            public static final String PASSWORD = "password";
            public static final String USERNAME = "username";
        }
    }


}
